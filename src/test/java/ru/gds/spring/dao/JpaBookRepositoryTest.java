package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
import ru.gds.spring.util.PrintUtils;

import java.io.File;
import java.util.*;

import static org.junit.Assume.assumeTrue;

@DataJpaTest
@Import({JpaBookRepository.class,
        JpaAuthorRepository.class,
        JpaGenreRepository.class,
        JpaStatusRepository.class})
class JpaBookRepositoryTest {

    @Autowired
    BookRepository jpaBookRepository;

    @Autowired
    AuthorRepository jpaAuthorRepository;

    @Autowired
    GenreRepository jpaGenreRepository;

    @Autowired
    StatusRepository jpaStatusRepository;

    private static final Logger logger = Logger.getLogger(JpaBookRepositoryTest.class);

    @Test
    void insertBookTest() {
        try {
            // Создание статуса
            Status status = new Status("archive");
            status = jpaStatusRepository.save(status);
            boolean result = status.getId() > 0;
            logger.debug("Статус добавлен: " + result);
            status = jpaStatusRepository.findById(1);
            assumeTrue(result && status != null);

            // Создание автора
            Author author = new Author(
                    "Михаил",
                    "Александрович",
                    "Шолохов",
                    new Date());
            author = jpaAuthorRepository.save(author);
            result = author.getId() > 0;
            logger.debug("Автор добавлен: " + result);
            List<Author> authors = jpaAuthorRepository.findAll();
            assumeTrue(result && authors.size() == 4);

            // Создание жанра
            Genre genre = new Genre("Исторический");
            genre = jpaGenreRepository.save(genre);
            result = genre.getId() > 0;
            logger.debug("Жанр добавлен: " + result);
            List<Genre> genres = jpaGenreRepository.findAll();
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
            book = jpaBookRepository.save(book);
            result = book.getId() > 0;
            logger.debug("Книга добавлена: " + result);
            assumeTrue(result);

            // Поиск всех
            List<Book> bookList = jpaBookRepository.findAll();
            logger.debug("Все книги: " + bookList);

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }

    @Test
    void updateBookTest() {
        try {
            List<Genre> genres = jpaGenreRepository.findAll();
            List<Author> authors = jpaAuthorRepository.findAll();
            Status status = jpaStatusRepository.findById(1);
            long bookId = 1;

            // Поиск по ID и обновление
            Book book = jpaBookRepository.findById(bookId);
            assumeTrue(book != null);
            book.setName("Кольцо тьмы обновление");
            book.setDescription("Сказки");
            book.setGenres(new HashSet<>(genres));
            book.setAuthors(new HashSet<>(authors));
            book.setStatus(status);
            File image = ResourceUtils.getFile("classpath:images/NPerumov_KoltsoTmy.jpg");
            book.setImage(FileUtils.convertFileToByteArray(image));
            boolean result = jpaBookRepository.updateById(book);
            logger.debug("Книга обновлена: " + result);
            assumeTrue(result);

            book = jpaBookRepository.findById(bookId);
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
            boolean result = jpaBookRepository.deleteById(1);
            logger.debug("Книга удалена: " + result);
            assumeTrue(result);

            List<Book> bookList = jpaBookRepository.findAll();
            logger.debug("Все книги: " + bookList);
            assumeTrue(bookList.size() == 1);

            // Убеждаемся что не отработало каскадное удаление
            List<Author> authors = jpaAuthorRepository.findAll();
            logger.debug("Все авторы: " + authors);
            assumeTrue(authors.size() == 3);

            List<Genre> genres = jpaGenreRepository.findAll();
            logger.debug("Все жанры: " + genres);
            assumeTrue(genres.size() == 3);

            List<Status> statuses = jpaStatusRepository.findAll();
            logger.debug("Все статусы: " + statuses);
            assumeTrue(statuses.size() == 2);

            authors = jpaAuthorRepository.findAll();
            assumeTrue(authors.size() == 3);

            genres = jpaGenreRepository.findAll();
            assumeTrue(genres.size() == 3);

            statuses = jpaStatusRepository.findAll();
            assumeTrue(statuses.size() == 2);

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }
}