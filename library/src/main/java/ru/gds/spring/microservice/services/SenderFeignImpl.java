package ru.gds.spring.microservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import ru.gds.spring.microservice.config.AppProperties;
import ru.gds.spring.microservice.constant.Constant;
import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.interfaces.BookContentServiceProxy;
import ru.gds.spring.microservice.interfaces.Sender;
import ru.gds.spring.microservice.params.ParamsBookContent;
import ru.gds.spring.microservice.util.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

@Service
@ConditionalOnProperty(name = "use", havingValue = "feign")
public class SenderFeignImpl implements Sender {

    private static final Logger logger = Logger.getLogger(SenderFeignImpl.class);

    private final BookContentServiceProxy bookContentServiceProxy;
    private final AppProperties appProperties;

    SenderFeignImpl(BookContentServiceProxy bookContentServiceProxy, AppProperties appProperties) {
        this.bookContentServiceProxy = bookContentServiceProxy;
        this.appProperties = appProperties;
    }

    @HystrixCommand(commandKey = "findByBookId", fallbackMethod = "findByBookIdBreaker")
    public String get(String bookId, int pageStart, int countPages) {
        logger.debug("library -> file-server findByBookId for bookId = " + bookId);

        // тестирование Hystrix
        sleepRandomly();

        return bookContentServiceProxy.findByBookId(bookId, pageStart, countPages);
    }

    @HystrixCommand(commandKey = "findFileByBookId", fallbackMethod = "findFileByBookIdBreaker")
    public String findFileByBookId(String bookId) {
        logger.debug("library -> file-server findFileByBookId for bookId = " + bookId);

        // тестирование Hystrix
        sleepRandomly();

        return bookContentServiceProxy.findFileByBookId(bookId);
    }

    public BookContentDto addUpdateBookContent(ParamsBookContent params) {
        logger.debug("library -> file-server add for bookId = " + params.getBookId());

        BookContentDto response = new BookContentDto(params.getBookId(), params.getBookName(), Constant.OK, "");
        try {

            BookContentDto saveResult = bookContentServiceProxy.add(
                    params.getBookId(),
                    params.getBookName(),
                    "title",
                    getMapFile(params.getFileTitle()));

            if (Constant.ERROR.equals(saveResult.getStatus()))
                throw new Exception(saveResult.getMessage());

            saveResult = bookContentServiceProxy.add(
                    params.getBookId(),
                    params.getBookName(),
                    "content",
                    getMapFile(params.getFileContent()));

            if (Constant.ERROR.equals(saveResult.getStatus()))
                throw new Exception(saveResult.getMessage());

            return response;

        } catch (Exception e) {
            logger.error("library -> file-server add error: " + Arrays.asList(e.getStackTrace()));

            response.setStatus(Constant.ERROR);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    public void delete(String uri) {
        logger.debug("library -> file-server delete");
    }

    private MultiValueMap<String, Object> getMapFile(MultipartFile multipartFile) throws IOException {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        };
        map.add("file", resource);
        map.add("fileType", multipartFile.getContentType());
        return map;
    }

    public String findByBookIdBreaker(String bookId, int pageStart, int countPages) {
        // Дефолтный ответ ля тестирования Hystrix
        return "{" +
                "   \"bookId\":\"5ec954efa59c3421ce2e1f5e\"," +
                "   \"bookName\":\"Кольцо тьмы\"," +
                "   \"startPage\":1," +
                "   \"countPages\":242," +
                "   \"pageList\":" +
                "   [" +
                "       {" +
                "           \"page\":1," +
                "           \"image\":null," +
                "           \"text\":\"Здравствуйте." +
                "               Похоже что сервис временно не доступен." +
                "               Приносим свои извинения." +
                "               Мы уже решаем эту проблему." +
                "               Пожалуйста попробуйте повторить запрос через несколько минут\"" +
                "       }," +
                "       {" +
                "           \"page\":2," +
                "           \"image\":null," +
                "           \"text\":\"\"" +
                "       }" +
                "   ]," +
                "   \"fileName\":null," +
                "   \"filePath\":null," +
                "   \"status\":null," +
                "   \"message\":null" +
                "}";
    }

    public String findFileByBookIdBreaker(String bookId) {
        // Для тестирования Hystrix нужно файл из resources/txt/defaultBookContent.txt
        // положить в директорию которая указана в bootstrap.yml в теге "fileDirectory"
        String fileDirectory = appProperties.getFileDirectory();
        byte[] defaultFileBytes = FileUtils.getFileBytes(fileDirectory + Constant.HYSTRIX_DEFAULT_FILE);
        return new String(defaultFileBytes, StandardCharsets.UTF_8);
    }

    // Для тестирования circuit breaker от Hystrix
    private void sleepRandomly() {
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum == 3) {
            System.out.println("Hystrix DEMONSTRATION");
            try {
                System.out.println("Start sleeping....." + System.currentTimeMillis());
                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.println("Hystrix thread interrupted...." + System.currentTimeMillis());
            }
        }
    }
}
