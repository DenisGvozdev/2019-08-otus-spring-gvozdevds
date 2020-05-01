package ru.gds.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.gds.spring.domain.Author;
import ru.gds.spring.interfaces.AuthorRepository;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void insertAuthorTest() {
        Author author = new Author(
                "Михаил",
                "Александрович",
                "Шолохов",
                new Date());
        author = authorRepository.save(author);
        assumeTrue(author.getId() != null);
    }

    @Test
    void updateAuthorTest() {
        Author author = getFirstAuthor();
        assumeTrue(author != null);

        String firstName = "Николай";
        author.setFirstName(firstName);
        author.setSecondName("Даниилович");
        author.setThirdName("Перумов");
        author.setBirthDate(new Date());
        author = authorRepository.save(author);
        assumeTrue(firstName.equals(author.getFirstName()));
    }

    @Test
    void deleteAuthorTest() {
        Author author = getAuthorByName("Временный");
        assumeTrue(author != null);

        authorRepository.deleteById(author.getId());

        author = getAuthorByName("Временный");
        assumeTrue(author == null);
    }

    private Author getFirstAuthor() {
        List<Author> authorList = authorRepository.findAll();
        return (!authorList.isEmpty()) ? authorList.get(0) : null;
    }

    private Author getAuthorByName(String name) {
        List<String> nameList = new ArrayList<>();
        nameList.add(name);
        List<Author> authorList = authorRepository.findAllByName(nameList, null);
        return (!authorList.isEmpty()) ? authorList.get(0) : null;
    }
}
