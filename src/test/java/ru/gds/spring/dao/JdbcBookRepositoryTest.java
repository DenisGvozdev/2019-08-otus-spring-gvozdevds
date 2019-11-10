package ru.gds.spring.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.GenreRepository;
import ru.gds.spring.interfaces.StatusRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/app-config.xml")
@Import({JdbcBookRepository.class,JdbcAuthorRepository.class,JdbcGenreRepository.class,JdbcStatusRepository.class})
public class JdbcBookRepositoryTest {

    @Autowired
    BookRepository jdbcBookRepository;

    @Autowired
    AuthorRepository jdbcAuthorRepository;

    @Autowired
    GenreRepository jdbcGenreRepository;

    @Autowired
    StatusRepository jdbcStatusRepository;

    @Test
    void insertBook() {
        Genre genre = jdbcGenreRepository.getById(1);
        Status status = jdbcStatusRepository.getById(1);
        Author author = jdbcAuthorRepository.getById(1);
        Book book = new Book("Мастер и Маргарита", new Date(), "Классика", null, genre, status, author);

        boolean result = jdbcBookRepository.insert(book);
        assumeTrue(result);
        System.out.println("Книга добавлена: " + result);

        List<Book> bookList = jdbcBookRepository.getAll();
        System.out.println("Все книги: " + bookList);
    }

    @Test
    void updateBook() {
        Genre genre = jdbcGenreRepository.getById(1);
        Status status = jdbcStatusRepository.getById(1);
        Author author = jdbcAuthorRepository.getById(1);
        Book book = jdbcBookRepository.getById(1);
        book.setName("Кольцо тьмы обновление");
        book.setDescription("Сказки");
        book.setImage(null);
        book.setGenre(genre);
        book.setAuthor(author);
        book.setStatus(status);

        boolean result = jdbcBookRepository.update(book);
        assumeTrue(result);
        System.out.println("Книга обновлена: " + result);

        book = jdbcBookRepository.getById(1);
        System.out.println("Новое название: " + book.getName());
    }

    @Test
    void getById() {
        Book book = jdbcBookRepository.getById(1);
        assumeTrue(book != null);
        assertEquals("Кольцо тьмы", book.getName());
        System.out.println(book.getName());
    }

    @Test
    void getAll() {
        List<Book> bookList = jdbcBookRepository.getAll();
        assumeTrue(bookList.size() == 2);
        System.out.println("Размер библиотеки: " + bookList.size());
    }

    @Test
    void removeBook() {
        boolean result = jdbcBookRepository.removeById(1);
        assumeTrue(result);
        System.out.println("Книга удалена: " + result);

        List<Book> bookList = jdbcBookRepository.getAll();
        System.out.println("Все книги: " + bookList);
    }

    @BeforeAll
    static void initAll() {
        System.out.println("---Inside initAll---");
    }

    @BeforeEach
    void init() {
        System.out.println("Start...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finished...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("---Inside tearDownAll---");
    }
}