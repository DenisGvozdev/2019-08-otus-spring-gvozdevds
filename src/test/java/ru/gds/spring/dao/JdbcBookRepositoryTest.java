package ru.gds.spring.dao;


import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.ResourceUtils;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.GenreRepository;
import ru.gds.spring.interfaces.StatusRepository;
import ru.gds.spring.util.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

@JdbcTest
@Import({JdbcBookRepository.class,
        JdbcAuthorRepository.class,
        JdbcGenreRepository.class,
        JdbcStatusRepository.class})
class JdbcBookRepositoryTest {

    @Autowired
    BookRepository jdbcBookRepository;

    @Autowired
    AuthorRepository jdbcAuthorRepository;

    @Autowired
    GenreRepository jdbcGenreRepository;

    @Autowired
    StatusRepository jdbcStatusRepository;

    private static final Logger logger = Logger.getLogger(JdbcBookRepositoryTest.class);

    @Test
    void insertBook() {
        try {
            Genre genre = jdbcGenreRepository.getById(1);
            Status status = jdbcStatusRepository.getById(1);
            Author author = jdbcAuthorRepository.getById(1);
            File image = ResourceUtils.getFile("classpath:images/MBulgakov_MasterIMargarita.jpg");
            Book book = new Book(
                    "Мастер и Маргарита",
                    new Date(),
                    "Классика",
                    FileUtils.convertFileToByteArray(image),
                    genre,
                    status,
                    author);

            boolean result = jdbcBookRepository.insert(book);
            assumeTrue(result);
            logger.debug("Книга добавлена: " + result);

            List<Book> bookList = jdbcBookRepository.getAll();
            logger.debug("Все книги: " + bookList);

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
        }
    }

    @Test
    void updateBook() {
        try {
            Genre genre = jdbcGenreRepository.getById(1);
            Status status = jdbcStatusRepository.getById(1);
            Author author = jdbcAuthorRepository.getById(1);
            Book book = jdbcBookRepository.getById(1);
            book.setName("Кольцо тьмы обновление");
            book.setDescription("Сказки");
            book.setGenre(genre);
            book.setAuthor(author);
            book.setStatus(status);
            File image = ResourceUtils.getFile("classpath:images/NPerumov_KoltsoTmy.jpg");
            book.setImage(FileUtils.convertFileToByteArray(image));

            boolean result = jdbcBookRepository.update(book);
            assumeTrue(result);
            logger.debug("Книга обновлена: " + result);

            book = jdbcBookRepository.getById(1);
            logger.debug("Новое название: " + book.getName());

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
        }
    }

    @Test
    void getById() {
        Book book = jdbcBookRepository.getById(1);
        assumeTrue(book != null);
        assertEquals("Кольцо тьмы", book.getName());
        logger.debug(book.getName());
    }

    @Test
    void getAll() {
        List<Book> bookList = jdbcBookRepository.getAll();
        assumeTrue(bookList.size() == 2);
        logger.debug("Размер библиотеки: " + bookList.size());
    }

    @Test
    void removeBook() {
        boolean result = jdbcBookRepository.removeById(1);
        assumeTrue(result);
        logger.debug("Книга удалена: " + result);

        List<Book> bookList = jdbcBookRepository.getAll();
        logger.debug("Все книги: " + bookList);
    }
}