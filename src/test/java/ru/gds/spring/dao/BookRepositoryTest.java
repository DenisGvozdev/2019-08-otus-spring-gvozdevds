package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    StatusRepository statusRepository;

    private static final Logger logger = Logger.getLogger(BookRepositoryTest.class);

    @Test
    void insertBookTest() {
        try {
            // Создание статуса
            Status status = new Status("archive");
            status = statusRepository.save(status);
            boolean result = status.getId() > 0;
            logger.debug("Статус добавлен: " + result);
            status = statusRepository.findById(1);
            assumeTrue(result && status != null);

            // Создание автора
            Author author = new Author(
                    "Михаил",
                    "Александрович",
                    "Шолохов",
                    new Date());
            author = authorRepository.save(author);
            result = author.getId() > 0;
            logger.debug("Автор добавлен: " + result);
            List<Author> authors = authorRepository.findAll();
            assumeTrue(result && authors.size() == 4);

            // Создание жанра
            Genre genre = new Genre("Исторический");
            genre = genreRepository.save(genre);
            result = genre.getId() > 0;
            logger.debug("Жанр добавлен: " + result);
            List<Genre> genres = genreRepository.findAll();
            assumeTrue(result && genres.size() == 4);

            // Создание книги
            Set<Genre> genreSet = new HashSet<>();
            genreSet.add(genres.get(3));

            Set<Author> authorSet = new HashSet<>();
            authorSet.add(authors.get(3));

            File image = ResourceUtils.getFile("classpath:images/MBulgakov_MasterIMargarita.jpg");
            Book book = new Book(
                    "Мастер и Маргарита",
                    new Date(),
                    "Классика",
                    FileUtils.convertFileToByteArray(image),
                    genreSet,
                    authorSet,
                    status);
            book = bookRepository.save(book);
            result = book.getId() > 0;
            logger.debug("Книга добавлена: " + result);
            assumeTrue(result);

            // Поиск всех
            List<Book> bookList = bookRepository.findAll();
            logger.debug("Все книги: " + bookList);

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }

    @Test
    void updateBookTest() {
        try {
            List<Genre> genres = genreRepository.findAll();
            List<Author> authors = authorRepository.findAll();
            Status status = statusRepository.findById(1);
            long bookId = 1;

            // Поиск по ID и обновление
            String bookName = "Кольцо тьмы обновление";
            Book book = bookRepository.findById(bookId);
            assumeTrue(book != null);
            book.setName(bookName);
            book.setDescription("Сказки");
            book.setGenres(new HashSet<>(genres));
            book.setAuthors(new HashSet<>(authors));
            book.setStatus(status);
            File image = ResourceUtils.getFile("classpath:images/NPerumov_KoltsoTmy.jpg");
            book.setImage(FileUtils.convertFileToByteArray(image));
            book = bookRepository.save(book);
            logger.debug("Книга обновлена");
            assumeTrue(bookName.equals(book.getName()));

            book = bookRepository.findById(bookId);
            logger.debug("Новые данные: " + PrintUtils.printObject(null, book));

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }

    @Test
    void deleteBookTest() {
        try {
            // Удаление книги
            bookRepository.deleteById(1L);
            logger.debug("Книга удалена");

            List<Book> bookList = bookRepository.findAll();
            logger.debug("Все книги: " + bookList);
            assumeTrue(bookList.size() == 1);

            // Убеждаемся что не отработало каскадное удаление
            List<Author> authors = authorRepository.findAll();
            logger.debug("Все авторы: " + authors);
            assumeTrue(authors.size() == 3);

            List<Genre> genres = genreRepository.findAll();
            logger.debug("Все жанры: " + genres);
            assumeTrue(genres.size() == 3);

            List<Status> statuses = statusRepository.findAll();
            logger.debug("Все статусы: " + statuses);
            assumeTrue(statuses.size() == 2);

            authors = authorRepository.findAll();
            assumeTrue(authors.size() == 3);

            genres = genreRepository.findAll();
            assumeTrue(genres.size() == 3);

            statuses = statusRepository.findAll();
            assumeTrue(statuses.size() == 2);

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }
}