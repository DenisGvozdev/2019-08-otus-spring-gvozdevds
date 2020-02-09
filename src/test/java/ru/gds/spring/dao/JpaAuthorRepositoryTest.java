package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.gds.spring.domain.Author;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.util.PrintUtils;

import java.util.Date;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataJpaTest
@Import({JpaAuthorRepository.class})
class JpaAuthorRepositoryTest {

    @Autowired
    AuthorRepository jpaAuthorRepository;

    private static final Logger logger = Logger.getLogger(JpaAuthorRepositoryTest.class);

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

        Author author = jpaAuthorRepository.findById(3);
        author.setFirstName("Николай");
        author.setSecondName("Даниилович");
        author.setThirdName("Перумов");
        author.setBirthDate(new Date());
        boolean result = jpaAuthorRepository.updateById(author);
        logger.debug("Автор обновлен: " + result);
        assumeTrue(result);

        author = jpaAuthorRepository.findById(3);
        logger.debug("Новые данные: " + PrintUtils.printObject(null, author));
    }

    @Test
    void deleteAuthorTest() {

        boolean result = jpaAuthorRepository.deleteById(3);
        logger.debug("Автор удален: " + result);
        assumeTrue(result);

        List<Author> authorList = jpaAuthorRepository.findAll();
        logger.debug("Все авторы: " + authorList);
        assumeTrue(authorList.size() == 2);
    }
}
