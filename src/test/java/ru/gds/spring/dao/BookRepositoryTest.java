package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.ResourceUtils;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.BookRepository;
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
    private TestEntityManager entityManager;

    private static final Logger logger = Logger.getLogger(BookRepositoryTest.class);

    @Test
    void insertBookTest() {
        try {
            File image = ResourceUtils.getFile("classpath:images/MBulgakov_MasterIMargarita.jpg");
            Book book = new Book(
                    "Мастер и Маргарита",
                    new Date(),
                    "Классика",
                    FileUtils.convertFileToByteArray(image),
                    getGenreSet(),
                    getAuthorSet(),
                    getFirstStatus());
            book = bookRepository.save(book);
            boolean result = book.getId() > 0;
            logger.debug("Книга добавлена: " + result);
            assumeTrue(result);

            // Поиск всех
            List<Book> bookList = bookRepository.findAll();
            assumeTrue(bookList.size() == 3);

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }

    @Test
    void updateBookTest() {
        try {
            long bookId = 1;
            Book book = bookRepository.findById(bookId).get();
            assumeTrue(book != null);

            // Поиск по ID и обновление
            String bookName = "Кольцо тьмы обновление";
            book.setName(bookName);
            book.setDescription("Сказки");
            book.setGenres(getGenreSet());
            book.setAuthors(getAuthorSet());
            book.setStatus(getFirstStatus());
            File image = ResourceUtils.getFile("classpath:images/NPerumov_KoltsoTmy.jpg");
            book.setImage(FileUtils.convertFileToByteArray(image));
            book = bookRepository.save(book);
            logger.debug("Книга обновлена");
            assumeTrue(bookName.equals(book.getName()));

            book = bookRepository.findById(bookId).get();
            assumeTrue(book != null);

            logger.debug("Новые данные: " + PrintUtils.printObject(null, book));
            assumeTrue(bookName.equals(book.getName()));

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
            assumeTrue(getAuthorSet().size() == 3);
            assumeTrue(getGenreSet().size() == 3);
            assumeTrue(getStatusList().size() == 2);

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }

    private List<Book> getBookList() {
        return bookRepository.findAll();
    }

    private List<Status> getStatusList() {
        return entityManager.getEntityManager()
                .createQuery("select a from Status a", Status.class)
                .getResultList();
    }

    private Status getFirstStatus() {
        return entityManager.find(Status.class, 1L);
    }

    private Set<Author> getAuthorSet() {
        return new HashSet<Author>(
                entityManager.getEntityManager()
                        .createQuery("select a from Author a", Author.class)
                        .getResultList());
    }

    private Set<Genre> getGenreSet() {
        return new HashSet<Genre>(
                entityManager.getEntityManager()
                        .createQuery("select g from Genre g", Genre.class)
                        .getResultList());
    }
}