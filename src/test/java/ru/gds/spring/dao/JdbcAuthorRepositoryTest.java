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
import ru.gds.spring.domain.Author;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@Import(JdbcAuthorRepository.class)
public class JdbcAuthorRepositoryTest {

    @Autowired
    @Qualifier("jdbcAuthorRepository")
    JdbcAuthorRepository jdbcAuthorRepository;

    @Test
    void insertAuthor() {
        Boolean result = jdbcAuthorRepository.insert("Михаил", "Александрович", "Шолохов", new Date(), 1);
        assumeTrue(result);
        System.out.println("Автор добавлен: " + result);

        List<Author> authorList = jdbcAuthorRepository.getAll();
        System.out.println("Все авторы: " + authorList);
    }

    @Test
    void updateAuthor() {
        Boolean result = jdbcAuthorRepository.update(1, "Николай", "Даниилович", "Перумов", new Date(), 1);
        assumeTrue(result);
        System.out.println("Автор обновлен: " + result);

        Author author = jdbcAuthorRepository.getById(1);
        System.out.println("Новые данные: " + author.toString());
    }

    @Test
    void getById() {
        Author author = jdbcAuthorRepository.getById(1);
        assumeTrue(author != null);
        assertEquals("Ник", author.getFirstName());
        System.out.println(author.getFirstName());
    }

    @Test
    void getAll() {
        List<Author> authorList = jdbcAuthorRepository.getAll();
        assumeTrue(authorList.size()==3);
        System.out.println("Количество авторов: " + authorList.size());
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
