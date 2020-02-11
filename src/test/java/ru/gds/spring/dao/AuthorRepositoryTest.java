package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.gds.spring.domain.Author;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.util.PrintUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository jpaAuthorRepository;

    private static final Logger logger = Logger.getLogger(AuthorRepositoryTest.class);

    @Test
    void insertAuthorTest() {

        Author author = new Author(
                "Михаил",
                "Александрович",
                "Шолохов",
                new Date());
        author = jpaAuthorRepository.save(author);
        long id = author.getId();
        boolean result = author.getId() > 0;
        logger.debug("Автор добавлен: " + result);
        assumeTrue(result);

        List<Author> authorList = jpaAuthorRepository.findAll();
        logger.debug("Все авторы: " + authorList);
    }

    @Test
    void updateAuthorTest() {
        long id = 3;
        String firstName = "Николай";

        Author author = jpaAuthorRepository.findById(id);
        author.setFirstName("Николай");
        author.setSecondName("Даниилович");
        author.setThirdName("Перумов");
        author.setBirthDate(new Date());
        author = jpaAuthorRepository.save(author);
        logger.debug("Автор обновлен");
        assumeTrue(firstName.equals(author.getFirstName()));

        author = jpaAuthorRepository.findById(id);
        logger.debug("Новые данные: " + PrintUtils.printObject(null, author));
    }

    @Test
    void findAuthorListTest() {
        List<Author> authorList = jpaAuthorRepository.findAllById(new ArrayList<Long>(Arrays.asList(1L, 2L)));
        assumeTrue(authorList.size() == 2);
        logger.debug("Авторы: " + PrintUtils.printObject(null, authorList));
    }

    @Test
    void deleteAuthorTest() {
        jpaAuthorRepository.deleteById(3L);
        logger.debug("Автор удален");

        List<Author> authorList = jpaAuthorRepository.findAll();
        logger.debug("Все авторы: " + authorList);
        assumeTrue(authorList.size() == 2);
    }
}
