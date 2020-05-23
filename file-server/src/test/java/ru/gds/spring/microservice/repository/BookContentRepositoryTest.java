package ru.gds.spring.microservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import ru.gds.spring.microservice.domain.BookContent;
import ru.gds.spring.microservice.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
@AutoConfigureTestDatabase
class BookContentRepositoryTest {

    @Autowired
    BookContentRepository bookContentRepository;

    @Autowired
    FileUtils fileUtils;

    @Test
    void insertBookContentTest() {
        File file = getFile();
        assumeTrue(file != null);

        List<String> bookPages = getBookPages(file);
        BookContent bookContent = new BookContent(
                null,
                "2",
                "Черное копье",
                new Date(),
                file.getAbsolutePath(),
                file.getName(),
                bookPages.size(),
                bookPages
        );
        bookContent = bookContentRepository.save(bookContent);
        assumeTrue(bookContent.getId() != null);
    }

    @Test
    void deleteBookContentTest() {
        BookContent bookContent = bookContentRepository.findByBookId("1");
        assumeTrue(bookContent != null && bookContent.getBookId() != null);

        bookContentRepository.deleteByBookId(bookContent.getBookId());

        bookContent = bookContentRepository.findByBookId("1");
        assumeTrue(bookContent == null);
    }

    private List<String> getBookPages(File file) {
        if (file == null)
            return new ArrayList<>();

        return fileUtils.getBookPages(file.getAbsolutePath());
    }

    private File getFile() {
        return fileUtils.getFile("classpath:files/NickPerumovChernoeKopye.txt");
    }
}
