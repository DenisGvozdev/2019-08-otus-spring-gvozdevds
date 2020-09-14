package ru.gds.spring.microservice.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileUtils {

    private static final Logger logger = Logger.getLogger(FileUtils.class);

    private static final int COUNT_WORDS_ON_PAGE = 200;

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

    private File getFileByFilePath(String filePath) {
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

    public File getFile(String filePath) {
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

    public String getTextBookFromFilePath(String filePath) {
        List<String> bookPageList = new ArrayList<>();
        try {

            StringBuilder text = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                //if (!org.springframework.util.StringUtils.isEmpty(line)) {
                    text.append(line + "\\n");
                //}
            }

            reader.close();
            return text.toString();

        } catch (Exception e) {
            logger.error("file not found: " + Arrays.asList(e.getStackTrace()));
        }
        return null;
    }

    public List<String> getBookPages(String filePath) {
        List<String> bookPageList = new ArrayList<>();
        try {

            StringBuilder text = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (!org.springframework.util.StringUtils.isEmpty(line)) {
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

    public File saveFile(String name, String directory, byte[] bytes) {
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

    public MultipartFile getMultipartFile(String filePath) {
        try {
            File file = getFile(filePath);
            if (file != null)
                return new MockMultipartFile(file.getName(), new FileInputStream(file));

        } catch (Exception e) {
            logger.error("getMultipartFile error: " + Arrays.asList(e.getStackTrace()));
        }
        return null;
    }
}