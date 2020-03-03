package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.gds.spring.domain.Author;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.util.PrintUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    private static final Logger logger = Logger.getLogger(AuthorRepositoryTest.class);

    @Test
    void insertAuthorTest() {

        Author author = new Author(
                "Михаил",
                "Александрович",
                "Шолохов",
                new Date());
        authorRepository.save(author);
        logger.debug("Автор добавлен");

        List<Author> authorList = getAuthorList();
        logger.debug("Все авторы: " + authorList);
    }

    @Test
    void updateAuthorTest() {
        Author author = getFirstAuthor();
        String id = author.getId();

        String firstName = "Николай";
        author.setFirstName(firstName);
        author.setSecondName("Даниилович");
        author.setThirdName("Перумов");
        author.setBirthDate(new Date());
        author = authorRepository.save(author);
        logger.debug("Автор обновлен");
        assumeTrue(firstName.equals(author.getFirstName()));

        author = getAuthorById(id);
        logger.debug("Новые данные: " + PrintUtils.printObject(null, author));
    }

    @Test
    void findAuthorListByIdTest() {
        List<Author> authorList = authorRepository.findAllById(getAllIdList(), null);
        logger.debug("Авторы: " + PrintUtils.printObject(null, authorList));
        assumeTrue(authorList.size() == 4);
        logger.debug("Авторы: " + PrintUtils.printObject(null, authorList));
    }

    @Test
    void deleteAuthorTest() {
        Author author = new Author("Автор", "Для", "Удаления", new Date());
        author = authorRepository.save(author);

        authorRepository.deleteById(author.getId());
        logger.debug("Автор удален");

        List<Author> authorList = getAuthorList();
        logger.debug("Все авторы: " + authorList);
        assumeTrue(authorList.size() == 4);
    }

    private Author getAuthorById(String id) {
        return authorRepository.findById(id).get();
    }

    private Author getFirstAuthor() {
        List<Author> authorList = authorRepository.findAll();
        return authorList.get(0);
    }

    private List<Author> getAuthorList() {
        return authorRepository.findAll();
    }

    private List<String> getAllIdList() {
        List<String> idList = new ArrayList<>();
        List<Author> authorList = authorRepository.findAll();
        for (Author author : authorList) {
            idList.add(author.getId());
        }
        return idList;
    }
}
