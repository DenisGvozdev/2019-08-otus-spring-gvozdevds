package ru.gds.spring.microservice.http;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gds.spring.microservice.config.AppProperties;
import ru.gds.spring.microservice.interfaces.Sender;
import ru.gds.spring.microservice.params.ParamsBookContent;

import java.nio.charset.StandardCharsets;

@Service
public class SenderImpl implements Sender {

    private static final Logger logger = Logger.getLogger(SenderImpl.class);

    private String server;
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public SenderImpl(AppProperties appProperties) {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        this.server = appProperties.getFileServerUrl();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
        this.rest.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public String get(String uri) {
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
            ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
            this.setStatus(responseEntity.getStatusCode());
            return responseEntity.getBody();

        } catch (Exception e) {
            logger.error(getMessage("get", uri, null, e.getMessage()));
            return null;
        }
    }


    public void post(String uri, String json) {
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
            ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);
            this.setStatus(responseEntity.getStatusCode());

        } catch (Exception e) {
            logger.error(getMessage("post", uri, json, e.getMessage()));
        }
    }

    public void postMVC(String uri, ParamsBookContent params) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadFile = new HttpPost(server + uri);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            ContentType textPlainUtf8 = ContentType.TEXT_PLAIN.withCharset("UTF-8");
            builder.addTextBody("bookId", params.getBookId(), textPlainUtf8);
            builder.addTextBody("bookName", params.getBookName(), textPlainUtf8);
            builder.addTextBody("countPages", "0", textPlainUtf8);
            builder.addTextBody("startPage", "0", textPlainUtf8);

            builder.addBinaryBody(
                    "fileTitle",
                    params.getFileTitle().getInputStream(),
                    ContentType.APPLICATION_OCTET_STREAM,
                    params.getFileTitle().getOriginalFilename()
            );
            builder.addBinaryBody(
                    "fileContent",
                    params.getFileContent().getInputStream(),
                    ContentType.APPLICATION_OCTET_STREAM,
                    params.getFileContent().getOriginalFilename()
            );
            org.apache.http.HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            httpClient.execute(uploadFile);

        } catch (Exception e) {
            logger.error(getMessage("postMVC", uri, params.toString(), e.getMessage()));
        }
    }

    public void put(String uri, String json) {
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
            ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.PUT, requestEntity, String.class);
            this.setStatus(responseEntity.getStatusCode());

        } catch (Exception e) {
            logger.error(getMessage("put", uri, json, e.getMessage()));
        }

    }

    public void delete(String uri) {
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
            ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.DELETE, requestEntity, String.class);
            this.setStatus(responseEntity.getStatusCode());

        } catch (Exception e) {
            logger.error(getMessage("delete", uri, null, e.getMessage()));
        }
    }

    private String getMessage(String method, String uri, Object params, String error) {
        return "call method: " + method + " by uri: " + uri + " with params " + params + "error: " + error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
