package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreRepository;
import ru.gds.spring.util.PrintUtils;

import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    GenreRepository jpaGenreRepository;

    private static final Logger logger = Logger.getLogger(GenreRepositoryTest.class);

    @Test
    void insertGenreTest() {

        Genre genre = new Genre("Исторический");
        genre = jpaGenreRepository.save(genre);
        boolean result = genre.getId() > 0;
        logger.debug("Жанр добавлен: " + result);
        assumeTrue(result);

        List<Genre> genreList = jpaGenreRepository.findAll();
        logger.debug("Все жанры: " + genreList);
        assumeTrue(genreList.size() == 4);
    }

    @Test
    void updateGenreTest() {

        List<Genre> genreList = jpaGenreRepository.findAll();
        assumeTrue(genreList.size() == 3);
        Genre genre = genreList.get(1);

        String genreName = "Обновленный жанр";
        genre.setName(genreName);
        genre = jpaGenreRepository.save(genre);
        logger.debug("Жанр обновлен");
        assumeTrue(genreName.equals(genre.getName()));

        genre = jpaGenreRepository.findById(genre.getId());
        logger.debug("Новые данные: " + PrintUtils.printObject(null, genre));
    }

    @Test
    void deleteGenreTest() {

        List<Genre> genreList = jpaGenreRepository.findAll();
        assumeTrue(genreList.size() == 3);
        Genre genre = genreList.get(1);

        jpaGenreRepository.deleteById(genre.getId());
        logger.debug("Жанр удален");

        genreList = jpaGenreRepository.findAll();
        logger.debug("Все жанры: " + genreList);
        assumeTrue(genreList.size() == 2);
    }
}
