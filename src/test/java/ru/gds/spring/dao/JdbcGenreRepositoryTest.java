package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

@JdbcTest
@Import(JdbcGenreRepository.class)
class JdbcGenreRepositoryTest {

    @Autowired
    GenreRepository jdbcGenreRepository;

    private static final Logger logger = Logger.getLogger(JdbcGenreRepositoryTest.class);

    @Test
    void insertGenre() {
        Genre genre = new Genre("Исторический");
        boolean result = jdbcGenreRepository.insert(genre);
        assumeTrue(result);
        logger.debug("Жанр добавлен: " + result);

        List<Genre> genreList = jdbcGenreRepository.getAll();
        logger.debug("Все жанры: " + genreList);
    }

    @Test
    void updateGenre() {
        Genre genre = jdbcGenreRepository.getById(1);
        genre.setName("Фэнтези+");
        boolean result = jdbcGenreRepository.update(genre);
        assumeTrue(result);
        logger.debug("Жанр обновлен: " + result);

        genre = jdbcGenreRepository.getById(1);
        logger.debug("Новые данные: " + genre.toString());
    }

    @Test
    void getById() {
        Genre genre = jdbcGenreRepository.getById(1);
        assumeTrue(genre != null);
        assertEquals("Фэнтези", genre.getName());
        logger.debug(genre.getName());
    }

    @Test
    void getAll() {
        List<Genre> genreList = jdbcGenreRepository.getAll();
        assumeTrue(genreList.size() == 3);
        logger.debug("Количество жанров: " + genreList.size());
    }

    @Test
    void removeGenre() {
        boolean result = jdbcGenreRepository.removeById(3);
        assumeTrue(result);
        logger.debug("Жанр удален: " + result);

        List<Genre> genreList = jdbcGenreRepository.getAll();
        logger.debug("Все жанры: " + genreList);
    }
}
