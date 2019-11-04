package ru.gds.spring.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gds.spring.config.Config;
import ru.gds.spring.domain.Book;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@Import(JdbcBookRepository.class)
public class JdbcBookRepositoryTest {

    @Autowired
    @Qualifier("jdbcBookRepository")
    JdbcBookRepository jdbcBookRepository;

    @Test
    void insertBook() {
        Boolean result = jdbcBookRepository.insert("Мастер и Маргарита", new Date(), "Классика", null, 1, 1, 1);
        assumeTrue(result);
        System.out.println("Книга добавлена: " + result);

        List<Book> bookList = jdbcBookRepository.getAll();
        System.out.println("Все книги: " + bookList);
    }

    @Test
    void updateBook() {
        Boolean result = jdbcBookRepository.update(1, "Кольцо тьмы обновление", new Date(), "Сказки", null, 1, 1, 1);
        assumeTrue(result);
        System.out.println("Книга обновлена: " + result);

        Book book = jdbcBookRepository.getById(1);
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
        assumeTrue(bookList.size()==2);
        System.out.println("Размер библиотеки: " + bookList.size());
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