package ru.gds.spring.microservice.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

public class FileUtils {

    private static final Logger logger = Logger.getLogger(FileUtils.class);

    public static byte[] getFileBytes(String filePath) {
        logger.debug("start getFileBytes: " + filePath);

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new Exception("file not found: " + filePath);
            }
            if (!file.isFile()) {
                throw new Exception("this is directory: " + filePath);
            }
            byte[] bytesArray = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

            return bytesArray;

        } catch (Exception e) {
            logger.error("file not found: " + filePath);
            return "".getBytes();
        }
    }

    public static MultipartFile byteArrayToMultipartFile(byte[] bytes, String fileName) {
        try {
            if (bytes == null)
                throw new Exception("byteArrayToMultipartFile bytes is null");

            if (StringUtils.isEmpty(fileName))
                throw new Exception("byteArrayToMultipartFile fileName is empty");

            FileItem fileItem = new DiskFileItem(
                    "fileData",
                    "application/text",
                    true,
                    fileName,
                    100000000,
                    new File(System.getProperty("java.io.tmpdir")));

            return new CommonsMultipartFile(fileItem);

        } catch (Exception e) {
            logger.error("byteArrayToMultipartFile error: " + Arrays.asList(e.getStackTrace()));
            return null;
        }
    }
}