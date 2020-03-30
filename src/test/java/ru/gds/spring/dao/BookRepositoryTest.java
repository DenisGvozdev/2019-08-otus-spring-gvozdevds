package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.ResourceUtils;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.*;
import ru.gds.spring.util.FileUtils;
import ru.gds.spring.util.PrintUtils;

import java.io.File;
import java.util.*;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class BookRepositoryTest {

//    @Autowired
//    BookRepository bookRepository;
//
//    @Autowired
//    MongoTemplate mongoTemplate;

    private static final Logger logger = Logger.getLogger(BookRepositoryTest.class);

//    @Test
//    void insertBookTest() {
//        try {
//            File image = ResourceUtils.getFile("classpath:images/MBulgakov_MasterIMargarita.jpg");
//            Book book = new Book(
//                    "Мастер и Маргарита",
//                    new Date(),
//                    "Классика",
//                    FileUtils.convertFileToByteArray(image),
//                    getGenreList(),
//                    getAuthorList(),
//                    getFirstStatus());
//            bookRepository.save(book);
//            logger.debug("Книга добавлена");
//
//            List<Book> bookList = getBookList();
//            logger.debug("Все книги: " + bookList);
//            assumeTrue(bookList.size() == 3);
//
//        } catch (Exception e) {
//            logger.error(Arrays.asList(e.getStackTrace()));
//            assumeTrue(false);
//        }
//    }
//
//    @Test
//    void updateBookTest() {
//        try {
//            Book book = getFirstBook();
//            assumeTrue(book != null);
//
//            String bookId = book.getId();
//            String bookName = "Кольцо тьмы обновление";
//            book.setName(bookName);
//            book.setDescription("Сказки");
//            book.setGenres(getGenreList());
//            book.setAuthors(getAuthorList());
//            book.setStatus(getFirstStatus());
//            File image = ResourceUtils.getFile("classpath:images/NPerumov_KoltsoTmy.jpg");
//            book.setImage(FileUtils.convertFileToByteArray(image));
//            book = bookRepository.save(book);
//            logger.debug("Книга обновлена");
//            assumeTrue(bookName.equals(book.getName()));
//
//            book = getBookById(bookId);
//            logger.debug("Новые данные: " + PrintUtils.printObject(null, book));
//
//        } catch (Exception e) {
//            logger.error(Arrays.asList(e.getStackTrace()));
//            assumeTrue(false);
//        }
//    }
//
//    @Test
//    void deleteBookTest() {
//        try {
//            Book book = getFirstBook();
//            assumeTrue(book != null);
//
//            String bookId = book.getId();
//            bookRepository.deleteById(bookId);
//            logger.debug("Книга удалена");
//
//            List<Book> bookList = getBookList();
//            logger.debug("Все книги: " + bookList);
//            assumeTrue(bookList.size() == 2);
//
//            // Убеждаемся что не отработало каскадное удаление
//            assumeTrue(getAuthorList().size() == 3);
//            assumeTrue(getGenreList().size() == 3);
//            assumeTrue(getStatusList().size() == 2);
//
//        } catch (Exception e) {
//            logger.error(Arrays.asList(e.getStackTrace()));
//            assumeTrue(false);
//        }
//    }
//
//    private Book getBookById(String id) {
//        return bookRepository.findById(id).get();
//    }
//
//    private Book getFirstBook() {
//        List<Book> bookList = bookRepository.findAll();
//        return bookList.get(0);
//    }
//
//    private List<Book> getBookList() {
//        return bookRepository.findAll();
//    }
//
//    private List<Author> getAuthorList() {
//        return mongoTemplate.findAll(Author.class, "authors");
//    }
//
//    private List<Genre> getGenreList() {
//        return mongoTemplate.findAll(Genre.class, "genres");
//    }
//
//    private Status getFirstStatus() {
//        List<Status> list = mongoTemplate.findAll(Status.class, "statuses");
//        return (list.isEmpty()) ? null : list.get(0);
//    }
//
//    private List<Status> getStatusList() {
//        return mongoTemplate.findAll(Status.class, "statuses");
//    }
}