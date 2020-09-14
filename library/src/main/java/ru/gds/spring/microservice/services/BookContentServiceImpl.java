package ru.gds.spring.microservice.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import ru.gds.spring.microservice.config.AppProperties;
import ru.gds.spring.microservice.constant.Constant;
import ru.gds.spring.microservice.domain.BookContent;
import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.dto.PageDto;
import ru.gds.spring.microservice.interfaces.BookContentService;
import ru.gds.spring.microservice.params.ParamsBookContent;
import ru.gds.spring.microservice.repository.BookContentRepository;
import ru.gds.spring.microservice.util.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class BookContentServiceImpl implements BookContentService {

    private static final Logger logger = Logger.getLogger(BookContentServiceImpl.class);

    private final BookContentRepository bookContentRepository;
    private final AppProperties appProperties;
    private final FileUtils fileUtils;

    public BookContentServiceImpl(
            BookContentRepository bookContentRepository,
            AppProperties appProperties,
            FileUtils fileUtils) {

        this.bookContentRepository = bookContentRepository;
        this.appProperties = appProperties;
        this.fileUtils = fileUtils;
    }

    public BookContentDto getPagesForBook(String bookId, int pageStart, int countPages) {
        BookContentDto bookContentDto = new BookContentDto();
        try {
            if (StringUtils.isEmpty(bookId)) {
                logger.error("return empty BookContentDto because bookId is empty");
                return bookContentDto;
            }

            BookContent bookContent = bookContentRepository.findByBookId(bookId);
            if (bookContent == null) {
                logger.error("return because bookContent not found by bookId = " + bookId);
                return bookContentDto;
            }

            List<String> pages = bookContent.getPages();
            int lastPage = pageStart + countPages;
            List<String> subPages;
            if (pages.size() >= lastPage) {
                subPages = pages.subList(pageStart, lastPage);
            } else {
                subPages = pages;
            }

            int pageNumber = pageStart;
            for (String text : subPages) {
                PageDto pageDto = new PageDto();
                pageDto.setPage(pageNumber++);
                pageDto.setText(text);
                bookContentDto.getPageList().add(pageDto);
            }

            bookContentDto.setBookId(bookContent.getBookId());
            bookContentDto.setBookName(bookContent.getBookName());
            bookContentDto.setStartPage(pageStart);
            bookContentDto.setCountPages(subPages.size());

            return bookContentDto;

        } catch (Exception e) {
            logger.error("Error getPagesForBook: " + Arrays.asList(e.getStackTrace()));
            return bookContentDto;
        }
    }

    public String findFileByBookId(String bookId) {
        try {
            if (StringUtils.isEmpty(bookId)) {
                logger.error("return empty BookContentDto because bookId is empty");
                return null;
            }

            BookContent bookContent = bookContentRepository.findByBookId(bookId);
            if (bookContent == null) {
                logger.error("return because bookContent not found by bookId = " + bookId);
                return null;
            }

            List<String> bookPages = fileUtils.getBookPages(bookContent.getContentFilePath());
            if (bookPages == null || bookPages.isEmpty())
                throw new Exception("file not found by path: " + bookContent.getContentFilePath());

            StringBuilder sb = new StringBuilder();
            int pageIndx = 1;
            for(String page : bookPages){
                sb.append("\n\n").append("Страница ").append(pageIndx).append("\n\n").append(page);
                pageIndx += 1;
            }
            return sb.toString();

        } catch (Exception e) {
            logger.error("Error save contentBook: " + Arrays.asList(e.getStackTrace()));
            return null;
        }
    }

    public BookContentDto save(String bookId, String bookName, String fileType, MultipartFile file) {
        try {
            if (StringUtils.isEmpty(bookId))
                throw new Exception("Book id is empty");

            if (Constant.FILE_TYPE_TITLE.equalsIgnoreCase(fileType))
                return saveBookTitle(bookId, bookName, file);

            if (Constant.FILE_TYPE_CONTENT.equalsIgnoreCase(fileType))
                return saveBookContent(bookId, bookName, file);

            BookContentDto bookContentDto = new BookContentDto();
            bookContentDto.setBookId(bookId);
            bookContentDto.setBookName(bookName);
            bookContentDto.setStatus(Constant.ERROR);
            bookContentDto.setMessage("fileType not recognized");

            return bookContentDto;

        } catch (Exception e) {
            logger.error("Error save contentBook: " + Arrays.asList(e.getStackTrace()));
            return new BookContentDto();
        }
    }

    public String deleteByBookId(String bookId) {
        try {
            if (bookId == null)
                return "Book id is null";

            bookContentRepository.deleteByBookId(bookId);
            return "Содержимое книги успешно удалено";

        } catch (Exception e) {
            return "Ошибка удаления содержимого книги: " + e.getMessage();
        }
    }

    private BookContentDto saveBookTitle(String bookId, String bookName, MultipartFile fileTitle) {
        BookContentDto bookContentDto = new BookContentDto();
        try {

            bookContentDto.setBookId(bookId);
            bookContentDto.setBookName(bookName);

            if (fileTitle == null) {
                logger.error("saveBookTitle error: fileTitle is null");
                bookContentDto.setStatus(Constant.ERROR);
                bookContentDto.setMessage("fileTitle is null");
                return bookContentDto;
            }

            String fileNameTitle = (!StringUtils.isEmpty(fileTitle.getOriginalFilename()))
                    ? fileTitle.getOriginalFilename()
                    : fileTitle.getName();

            String fileDirectory = appProperties.getFileDirectory();
            String fileName = bookId + "_" + fileNameTitle;
            fileUtils.saveFile(fileName, fileDirectory, fileTitle.getBytes());

            bookContentDto.setFileName(fileName);
            bookContentDto.setFilePath(fileDirectory + fileName);
            bookContentDto.setStatus(Constant.OK);
            return bookContentDto;

        } catch (Exception e) {
            logger.error(" saveBookTitle error: " + Arrays.asList(e.getStackTrace()));

            bookContentDto.setStatus(Constant.ERROR);
            bookContentDto.setMessage(e.getMessage());
            return bookContentDto;
        }
    }

    private BookContentDto saveBookContent(String bookId, String bookName, MultipartFile file) {
        try {

            if (file == null) {
                BookContent bookContent = bookContentRepository.findByBookId(bookId);
                file = fileUtils.getMultipartFile(bookContent.getContentFilePath());
            }

            if (file == null) {
                logger.error("saveBookContent error: multipartFileContent is null");
                return new BookContentDto();
            }

            String fileNameContent = (!StringUtils.isEmpty(file.getOriginalFilename()))
                    ? file.getOriginalFilename()
                    : file.getName();

            String fileDirectory = appProperties.getFileDirectory();
            String fileName = bookId + "_" + fileNameContent;
            File fileContent = fileUtils.saveFile(fileName, fileDirectory, file.getBytes());

            if (fileContent == null) {
                logger.error("file content not found by path: " + fileDirectory + fileName);
                return new BookContentDto();
            }

            List<String> bookPages = fileUtils.getBookPages(fileContent.getAbsolutePath());

            BookContent bookContent = new BookContent(
                    bookId,
                    bookId,
                    bookName,
                    new Date(),
                    fileContent.getAbsolutePath(),
                    fileName,
                    bookPages.size(),
                    bookPages
            );

            bookContent = bookContentRepository.save(bookContent);
            return BookContentDto.toDtoLight(bookContent);

        } catch (Exception e) {
            logger.error("saveBookContent error: " + Arrays.asList(e.getStackTrace()));
            return new BookContentDto();
        }
    }

    public BookContentDto addUpdateBookContent(ParamsBookContent params) {
        logger.debug("library -> file-server add for bookId = " + params.getBookId());

        BookContentDto response = new BookContentDto(
                params.getBookId(), params.getBookName(), Constant.OK, "");
        try {

            BookContentDto saveResult = save(params.getBookId(),
                    params.getBookName(),
                    "title",
                    params.getFileTitle());

            if (Constant.ERROR.equals(saveResult.getStatus()))
                throw new Exception(saveResult.getMessage());

            saveResult = save(params.getBookId(),
                    params.getBookName(),
                    "content",
                    params.getFileContent());

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
}