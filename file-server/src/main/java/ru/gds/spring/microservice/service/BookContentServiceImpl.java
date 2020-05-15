package ru.gds.spring.microservice.service;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.gds.spring.config.AppProperties;
import ru.gds.spring.microservice.domain.BookContent;
import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.dto.PageDto;
import ru.gds.spring.microservice.interfaces.BookContentService;
import ru.gds.spring.microservice.params.ParamsBookContent;
import ru.gds.spring.microservice.repository.BookContentRepository;
import ru.gds.spring.microservice.util.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class BookContentServiceImpl implements BookContentService {

    private static final Logger logger = Logger.getLogger(BookContentServiceImpl.class);

    private final BookContentRepository bookContentRepository;
    private final AppProperties appProperties;

    public BookContentServiceImpl(BookContentRepository bookContentRepository, AppProperties appProperties) {
        this.bookContentRepository = bookContentRepository;
        this.appProperties = appProperties;
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

    public FileSystemResource findFileByBookId(String bookId) {
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

            File file = FileUtils.getFile(bookContent.getContentFilePath());
            if (file == null)
                throw new Exception("file not found by path: " + bookContent.getContentFilePath());

            return new FileSystemResource(file);

        } catch (Exception e) {
            logger.error("Error save contentBook: " + Arrays.asList(e.getStackTrace()));
            return null;
        }
    }

    public BookContentDto save(ParamsBookContent params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getBookId()))
                throw new Exception("Book id is empty");

            saveBookTitle(params);

            return saveBookContent(params);

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

    private void saveBookTitle(ParamsBookContent params) {
        try {
            MultipartFile multipartFileTitle = params.getFileTitle();
            if (multipartFileTitle == null) {
                logger.error("saveBookTitle error: multipartFileTitle is null");
                return;
            }

            String fileNameTitle = (!StringUtils.isEmpty(multipartFileTitle.getOriginalFilename()))
                    ? multipartFileTitle.getOriginalFilename()
                    : multipartFileTitle.getName();

            String fileDirectory = appProperties.getFileDirectory();
            FileUtils.saveFile(fileNameTitle, fileDirectory, multipartFileTitle.getBytes());

        } catch (Exception e) {
            logger.error(" saveBookTitle error: " + Arrays.asList(e.getStackTrace()));
        }
    }

    private BookContentDto saveBookContent(ParamsBookContent params) {
        try {
            MultipartFile multipartFileContent = params.getFileContent();

            if (multipartFileContent == null) {
                BookContent bookContent = bookContentRepository.findByBookId(params.getBookId());
                multipartFileContent = FileUtils.getMultipartFile(bookContent.getContentFilePath());
            }

            if (multipartFileContent == null) {
                logger.error("saveBookContent error: multipartFileContent is null");
                return new BookContentDto();
            }

            String fileNameContent = (!StringUtils.isEmpty(multipartFileContent.getOriginalFilename()))
                    ? multipartFileContent.getOriginalFilename()
                    : multipartFileContent.getName();

            String fileDirectory = appProperties.getFileDirectory();
            File fileContent = FileUtils.saveFile(fileNameContent, fileDirectory, params.getFileContent().getBytes());

            if (fileContent == null) {
                logger.error("file content not found by path: " + fileDirectory + fileNameContent);
                return new BookContentDto();
            }

            List<String> bookPages = FileUtils.getBookPages(fileContent.getAbsolutePath());

            BookContent bookContent = new BookContent(
                    params.getBookId(),
                    params.getBookId(),
                    params.getBookName(),
                    new Date(),
                    fileContent.getAbsolutePath(),
                    fileContent.getName(),
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
}