package ru.gds.spring.microservice.util;

import org.apache.log4j.Logger;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    private static final Logger logger = Logger.getLogger(FileUtils.class);

    private static final int COUNT_WORDS_ON_PAGE = 200;

    private static File getFileByFilePath(String filePath) {
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

    public static byte[] getByteArrayByFilePath(String filePath) {
        try {
            File file = getFileByFilePath(filePath);
            return convertFileToByteArray(file);

        } catch (Exception e) {
            logger.error("file not found error: " + Arrays.asList(e.getStackTrace()));
            return "".getBytes();
        }
    }

    public static File getFile(String filePath) {
        try {
            File file = ResourceUtils.getFile(filePath);
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

    public static List<String> getBookPages(String filePath) {
        List<String> bookPageList = new ArrayList<>();
        try {

            StringBuilder text = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (!StringUtils.isEmpty(line)) {
                    text.append(line);
                }
            }

            int countWordsOnPage = COUNT_WORDS_ON_PAGE;
            StringBuilder page = new StringBuilder();

            String[] words = text.toString().split("\\s+");

            for (int indx = 0; indx < words.length; indx++) {

                if (countWordsOnPage == 0 || indx == words.length - 1) {
                    page.append(words[indx]).append(" ");
                    bookPageList.add(page.toString());
                    countWordsOnPage = COUNT_WORDS_ON_PAGE;
                    page = new StringBuilder();

                } else {
                    countWordsOnPage -= 1;
                    page.append(words[indx]).append(" ");
                }
            }
            reader.close();

        } catch (Exception e) {
            logger.error("file not found: " + Arrays.asList(e.getStackTrace()));
        }
        return bookPageList;
    }

    public static File saveFile(String name, String directory, byte[] bytes) {
        try {
            Path path = Paths.get(directory + name);
            Files.write(path, bytes);
            logger.debug("File " + directory + name + " success saved");
            return getFileByFilePath(directory + name);

        } catch (Exception e) {
            logger.error("saveFile error: " + Arrays.asList(e.getStackTrace()));
            return null;
        }
    }

    public static MultipartFile getMultipartFile(String filePath) {
        try {
            File file = getFile(filePath);
            if (file != null)
                return new MockMultipartFile(file.getName(), new FileInputStream(file));

        } catch (Exception e) {
            logger.error("getMultipartFile error: " + Arrays.asList(e.getStackTrace()));
        }
        return null;
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
}
