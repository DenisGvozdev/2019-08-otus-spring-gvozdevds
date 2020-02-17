package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreRepository;
import ru.gds.spring.util.PrintUtils;

import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring.mongo"})
class GenreRepositoryTest {

    @Autowired
    GenreRepository genreRepository;

    private static final Logger logger = Logger.getLogger(GenreRepositoryTest.class);

    @Test
    void insertGenreTest() {
        Genre genre = new Genre("Исторический");
        genreRepository.save(genre);
        logger.debug("Жанр добавлен");

        List<Genre> genreList = getGenreList();
        logger.debug("Все жанры: " + genreList);
        assumeTrue(genreList.size() == 4);
    }

    @Test
    void updateGenreTest() {
        Genre genre = getFirstGenre();

        String genreName = "Обновленный жанр";
        genre.setName(genreName);
        genre = genreRepository.save(genre);
        logger.debug("Жанр обновлен");
        assumeTrue(genreName.equals(genre.getName()));

        genre = getGenreById(genre.getId());
        logger.debug("Новые данные: " + PrintUtils.printObject(null, genre));
    }

    @Test
    void deleteGenreTest() {
        Genre genre = getFirstGenre();
        genreRepository.deleteById(genre.getId());
        logger.debug("Жанр удален");

        List<Genre> genreList = getGenreList();
        logger.debug("Все жанры: " + genreList);
        assumeTrue(genreList.size() == 2);
    }

    private Genre getGenreById(String id) {
        return genreRepository.findById(id).get();
    }

    private Genre getFirstGenre() {
        List<Genre> genreList = getGenreList();
        return genreList.get(0);
    }

    private List<Genre> getGenreList() {
        return genreRepository.findAll();
    }
}
