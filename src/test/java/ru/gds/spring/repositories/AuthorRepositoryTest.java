package ru.gds.spring.repositories;

//import org.apache.log4j.Logger;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import ru.gds.spring.microservice.domain.Author;
//import ru.gds.spring.microservice.interfaces.AuthorRepository;
//import ru.gds.spring.microservice.util.PrintUtils;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.Assume.assumeTrue;

//@DataJpaTest
class AuthorRepositoryTest {

//    @Autowired
//    AuthorRepository authorRepository;
//
//    private static final Logger logger = Logger.getLogger(AuthorRepositoryTest.class);
//
//    @Test
//    void insertAuthorTest() {
//        Author author = new Author(
//                "Михаил",
//                "Александрович",
//                "Шолохов",
//                new Date());
//        author = authorRepository.save(author);
//        boolean result = author.getId() > 0;
//        logger.debug("Автор добавлен: " + result);
//        assumeTrue(result);
//
//        List<Author> authorList = authorRepository.findAll();
//        logger.debug("Все авторы: " + authorList);
//        assumeTrue(authorList.size() == 4);
//    }
//
//    @Test
//    void updateAuthorTest() {
//        long id = 3;
//        Author author = authorRepository.findById(id).get();
//        String firstName = "Николай";
//        author.setFirstName(firstName);
//        author.setSecondName("Даниилович");
//        author.setThirdName("Перумов");
//        author.setBirthDate(new Date());
//        authorRepository.save(author);
//        logger.debug("Автор обновлен");
//
//        author = authorRepository.findById(id).get();
//        logger.debug("Новые данные: " + PrintUtils.printObject(null, author));
//        assumeTrue(firstName.equals(author.getFirstName()));
//    }
//
//    @Test
//    void findAuthorListTest() {
//        List<Author> authorList = authorRepository.findAllById(new ArrayList<Long>(Arrays.asList(1L, 2L)));
//        logger.debug("Авторы: " + PrintUtils.printObject(null, authorList));
//        assumeTrue(authorList.size() == 2);
//    }
//
//    @Test
//    void deleteAuthorTest() {
//        authorRepository.deleteById(3L);
//        logger.debug("Автор удален");
//
//        List<Author> authorList = authorRepository.findAll();
//        logger.debug("Все авторы: " + authorList);
//        assumeTrue(authorList.size() == 2);
//    }
}
