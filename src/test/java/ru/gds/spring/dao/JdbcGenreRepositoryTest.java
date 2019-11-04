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
import ru.gds.spring.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@Import(JdbcGenreRepository.class)
public class JdbcGenreRepositoryTest {

    @Autowired
    @Qualifier("jdbcGenreRepository")
    JdbcGenreRepository jdbcGenreRepository;

    @Test
    void insertGenre() {
        Boolean result = jdbcGenreRepository.insert("Исторический");
        assumeTrue(result);
        System.out.println("Жанр добавлен: " + result);

        List<Genre> genreList = jdbcGenreRepository.getAll();
        System.out.println("Все жанры: " + genreList);
    }

    @Test
    void updateGenre() {
        Boolean result = jdbcGenreRepository.update(1, "Фэнтези+");
        assumeTrue(result);
        System.out.println("Жанр обновлен: " + result);

        Genre genre = jdbcGenreRepository.getById(1);
        System.out.println("Новые данные: " + genre.toString());
    }

    @Test
    void getById() {
        Genre genre = jdbcGenreRepository.getById(1);
        assumeTrue(genre != null);
        assertEquals("Фэнтези", genre.getName());
        System.out.println(genre.getName());
    }

    @Test
    void getAll() {
        List<Genre> genreList = jdbcGenreRepository.getAll();
        assumeTrue(genreList.size() == 3);
        System.out.println("Количество жанров: " + genreList.size());
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
