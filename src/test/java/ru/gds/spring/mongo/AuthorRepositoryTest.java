package ru.gds.spring.mongo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gds.spring.mongo.domain.AuthorMongo;
import ru.gds.spring.mongo.interfaces.AuthorMongoRepository;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class AuthorRepositoryTest {

//    @Autowired
//    AuthorMongoRepository authorRepository;
//
//    @Test
//    void insertAuthorTest() {
//        AuthorMongo author = new AuthorMongo(
//                "Михаил",
//                "Александрович",
//                "Шолохов",
//                new Date());
//        author = authorRepository.save(author);
//        assumeTrue(author.getId() != null);
//    }
//
//    @Test
//    void updateAuthorTest() {
//        AuthorMongo author = getFirstAuthor();
//        assumeTrue(author != null);
//
//        String firstName = "Николай";
//        author.setFirstName(firstName);
//        author.setSecondName("Даниилович");
//        author.setThirdName("Перумов");
//        author.setBirthDate(new Date());
//        author = authorRepository.save(author);
//        assumeTrue(firstName.equals(author.getFirstName()));
//    }
//
//    @Test
//    void deleteAuthorTest() {
//        AuthorMongo author = getAuthorByName("Временный");
//        assumeTrue(author != null);
//
//        authorRepository.deleteById(author.getId());
//
//        author = getAuthorByName("Временный");
//        assumeTrue(author == null);
//    }
//
//    private AuthorMongo getFirstAuthor() {
//        List<AuthorMongo> authorList = authorRepository.findAll();
//        return (!authorList.isEmpty()) ? authorList.get(0) : null;
//    }
//
//    private AuthorMongo getAuthorByName(String name) {
//        List<String> nameList = new ArrayList<>();
//        nameList.add(name);
//        List<AuthorMongo> authorList = authorRepository.findAllByName(nameList, null);
//        return (!authorList.isEmpty()) ? authorList.get(0) : null;
//    }
}
