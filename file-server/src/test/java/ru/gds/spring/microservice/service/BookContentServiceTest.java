package ru.gds.spring.microservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;
import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.params.ParamsBookContent;
import ru.gds.spring.microservice.util.FileUtils;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class BookContentServiceTest {

    @Autowired
    BookContentService bookContentService;

    @Test
    void insertBookTest() {
        BookContentDto bookContentDto = saveBookContent("2", "Черное копье 2", getFile());
        assumeTrue(bookContentDto.getBookId() != null);
    }

    @Test
    void findFileByBookIdTest() {
        BookContentDto bookContentDto = saveBookContent("3", "Черное копье 3", getFile());
        assumeTrue(bookContentDto.getBookId() != null);

        FileSystemResource file = bookContentService.findFileByBookId(bookContentDto.getBookId());
        assumeTrue(file != null);
    }

    @Test
    void deleteBookTest() {
        BookContentDto bookContentDto = bookContentService.getPagesForBook("1", 1, 50);
        assumeTrue(bookContentDto.getBookId() != null);

        bookContentService.deleteByBookId(bookContentDto.getBookId());

        bookContentDto = bookContentService.getPagesForBook("1", 1, 50);
        assumeTrue(bookContentDto.getBookId() == null);
    }

    private BookContentDto saveBookContent(String bookId, String bookName, MultipartFile file) {
        return bookContentService.save(
                new ParamsBookContent(bookId, bookName, 1, 50, file));
    }

    private MultipartFile getFile() {
        return FileUtils.getMultipartFile("classpath:files/NickPerumovChernoeKopye.txt");
    }
}
