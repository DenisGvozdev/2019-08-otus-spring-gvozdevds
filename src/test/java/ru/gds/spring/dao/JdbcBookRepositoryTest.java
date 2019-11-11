package ru.gds.spring.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

@JdbcTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/app-config.xml")
@Import({JdbcBookRepository.class, JdbcAuthorRepository.class, JdbcGenreRepository.class, JdbcStatusRepository.class})
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
    public void insertBook() {
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
    public void updateBook() {
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
    public void getById() {
        Book book = jdbcBookRepository.getById(1);
        assumeTrue(book != null);
        assertEquals("Кольцо тьмы", book.getName());
        System.out.println(book.getName());
    }

    @Test
    public void getAll() {
        List<Book> bookList = jdbcBookRepository.getAll();
        assumeTrue(bookList.size() == 2);
        System.out.println("Размер библиотеки: " + bookList.size());
    }

    @Test
    public void removeBook() {
        boolean result = jdbcBookRepository.removeById(1);
        assumeTrue(result);
        System.out.println("Книга удалена: " + result);

        List<Book> bookList = jdbcBookRepository.getAll();
        System.out.println("Все книги: " + bookList);
    }
}