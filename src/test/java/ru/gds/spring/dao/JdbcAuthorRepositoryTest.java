package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.StatusRepository;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

@JdbcTest
@Import({JdbcAuthorRepository.class, JdbcStatusRepository.class})
class JdbcAuthorRepositoryTest {

    @Autowired
    AuthorRepository jdbcAuthorRepository;

    @Autowired
    StatusRepository jdbcStatusRepository;

    private static final Logger logger = Logger.getLogger(JdbcAuthorRepositoryTest.class);

    @Test
    void insertAuthor() {
        Status status = jdbcStatusRepository.getById(1);
        Author author = new Author("Михаил", "Александрович", "Шолохов", new Date(), status);
        boolean result = jdbcAuthorRepository.insert(author);
        assumeTrue(result);
        logger.debug("Автор добавлен: " + result);

        List<Author> authorList = jdbcAuthorRepository.getAll();
        logger.debug("Все авторы: " + authorList);
    }

    @Test
    void updateAuthor() {
        Status status = jdbcStatusRepository.getById(1);
        Author author = jdbcAuthorRepository.getById(1);
        author.setFirstName("Николай");
        author.setSecondName("Даниилович");
        author.setThirdName("Перумов");
        author.setBirthDate(new Date());
        author.setStatus(status);
        boolean result = jdbcAuthorRepository.update(author);
        assumeTrue(result);
        logger.debug("Автор обновлен: " + result);

        author = jdbcAuthorRepository.getById(1);
        logger.debug("Новые данные: " + author.toString());
    }

    @Test
    void getById() {
        Author author = jdbcAuthorRepository.getById(1);
        assumeTrue(author != null);
        assertEquals("Ник", author.getFirstName());
        logger.debug(author.getFirstName());
    }

    @Test
    void getAll() {
        List<Author> authorList = jdbcAuthorRepository.getAll();
        assumeTrue(authorList.size() == 3);
        logger.debug("Количество авторов: " + authorList.size());
    }

    @Test
    void removeBook() {
        boolean result = jdbcAuthorRepository.removeById(3);
        assumeTrue(result);
        logger.debug("Автор удален: " + result);

        List<Author> authorList = jdbcAuthorRepository.getAll();
        logger.debug("Все авторы: " + authorList);
    }
}
