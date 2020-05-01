package ru.gds.spring.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;

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
}
