package ru.gds.spring.util;

import org.apache.log4j.Logger;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class FileUtils {

    private static final Logger logger = Logger.getLogger(FileUtils.class);

    public static File getFile(String filePath) {
        logger.debug("start getFile: " + filePath);

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new Exception("file not found: " + filePath);
            }
            if (!file.isFile()) {
                throw new Exception("this is directory: " + filePath);
            }
            return file;

        } catch (Exception e) {
            logger.error("file not found: " + filePath);
            return null;
        }
    }

    public static byte[] convertFileToByteArray(File file) {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;
        try {
            if (file == null) {
                throw new Exception("file is null");
            }

            logger.debug("convert file: " + file.getAbsolutePath());
            bytesArray = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (Exception e) {
            logger.error("file not found: " + Arrays.asList(e.getStackTrace()));
            e.printStackTrace();

        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bytesArray;
    }

    public static byte[] convertFilePartToByteArray(FilePart filePart) {
        try {
            if (filePart == null || StringUtils.isEmpty(filePart.filename()))
                return "".getBytes();

            String file = filePart.filename();
            String fileName = file.substring(0, file.indexOf("."));
            String fileExtension = file.substring(file.indexOf(".") + 1, file.length());
            File tempFile = File.createTempFile(fileName, fileExtension);
            filePart.transferTo(tempFile.getAbsoluteFile());
            return convertFileToByteArray(tempFile);
        } catch (IOException e) {
            logger.error("error create temporary file: " + Arrays.asList(e.getStackTrace()));
            return "".getBytes();
        }
    }
}
