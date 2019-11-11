package ru.gds.spring.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.StatusRepository;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

@JdbcTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/app-config.xml")
@Import({JdbcAuthorRepository.class, JdbcStatusRepository.class})
public class JdbcAuthorRepositoryTest {

    @Autowired
    AuthorRepository jdbcAuthorRepository;

    @Autowired
    StatusRepository jdbcStatusRepository;

    @Test
    public void insertAuthor() {
        Status status = jdbcStatusRepository.getById(1);
        Author author = new Author("Михаил", "Александрович", "Шолохов", new Date(), status);
        boolean result = jdbcAuthorRepository.insert(author);
        assumeTrue(result);
        System.out.println("Автор добавлен: " + result);

        List<Author> authorList = jdbcAuthorRepository.getAll();
        System.out.println("Все авторы: " + authorList);
    }

    @Test
    public void updateAuthor() {
        Status status = jdbcStatusRepository.getById(1);
        Author author = jdbcAuthorRepository.getById(1);
        author.setFirstName("Николай");
        author.setSecondName("Даниилович");
        author.setThirdName("Перумов");
        author.setBirthDate(new Date());
        author.setStatus(status);
        boolean result = jdbcAuthorRepository.update(author);
        assumeTrue(result);
        System.out.println("Автор обновлен: " + result);

        author = jdbcAuthorRepository.getById(1);
        System.out.println("Новые данные: " + author.toString());
    }

    @Test
    public void getById() {
        Author author = jdbcAuthorRepository.getById(1);
        assumeTrue(author != null);
        assertEquals("Ник", author.getFirstName());
        System.out.println(author.getFirstName());
    }

    @Test
    public void getAll() {
        List<Author> authorList = jdbcAuthorRepository.getAll();
        assumeTrue(authorList.size() == 3);
        System.out.println("Количество авторов: " + authorList.size());
    }

    @Test
    public void removeBook() {
        boolean result = jdbcAuthorRepository.removeById(3);
        assumeTrue(result);
        System.out.println("Автор удален: " + result);

        List<Author> authorList = jdbcAuthorRepository.getAll();
        System.out.println("Все авторы: " + authorList);
    }
}
