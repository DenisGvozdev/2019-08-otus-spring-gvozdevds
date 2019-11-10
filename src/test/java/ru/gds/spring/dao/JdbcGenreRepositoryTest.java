package ru.gds.spring.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/app-config.xml")
@Import(JdbcGenreRepository.class)
public class JdbcGenreRepositoryTest {

    @Autowired
    GenreRepository jdbcGenreRepository;

    @Test
    void insertGenre() {
        Genre genre = new Genre("Исторический");
        boolean result = jdbcGenreRepository.insert(genre);
        assumeTrue(result);
        System.out.println("Жанр добавлен: " + result);

        List<Genre> genreList = jdbcGenreRepository.getAll();
        System.out.println("Все жанры: " + genreList);
    }

    @Test
    void updateGenre() {
        Genre genre = jdbcGenreRepository.getById(1);
        genre.setName("Фэнтези+");
        boolean result = jdbcGenreRepository.update(genre);
        assumeTrue(result);
        System.out.println("Жанр обновлен: " + result);

        genre = jdbcGenreRepository.getById(1);
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

    @Test
    void removeGenre() {
        boolean result = jdbcGenreRepository.removeById(3);
        assumeTrue(result);
        System.out.println("Жанр удален: " + result);

        List<Genre> genreList = jdbcGenreRepository.getAll();
        System.out.println("Все жанры: " + genreList);
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
