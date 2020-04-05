package ru.gds.spring;

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
    GenreRepository genreRepository;

    private static final Logger logger = Logger.getLogger(GenreRepositoryTest.class);

    @Test
    void insertGenreTest() {
        Genre genre = new Genre("Исторический");
        genre = genreRepository.save(genre);
        boolean result = genre.getId() > 0;
        logger.debug("Жанр добавлен: " + result);
        assumeTrue(result);

        List<Genre> genreList = getGenreList();
        logger.debug("Все жанры: " + genreList);
        assumeTrue(genreList.size() == 4);
    }

    @Test
    void updateGenreTest() {
        List<Genre> genreList = getGenreList();
        assumeTrue(genreList.size() == 3);
        Genre genre = genreList.get(1);

        String genreName = "Обновленный жанр";
        genre.setName(genreName);
        genre = genreRepository.save(genre);
        logger.debug("Жанр обновлен");

        genre = genreRepository.findById(genre.getId()).get();
        logger.debug("Новые данные: " + PrintUtils.printObject(null, genre));
        assumeTrue(genreName.equals(genre.getName()));
    }

    @Test
    void deleteGenreTest() {
        List<Genre> genreList = getGenreList();
        assumeTrue(genreList.size() == 3);
        Genre genre = genreList.get(1);

        genreRepository.deleteById(genre.getId());
        logger.debug("Жанр удален");

        genreList = getGenreList();
        logger.debug("Все жанры: " + genreList);
        assumeTrue(genreList.size() == 2);
    }

    private List<Genre> getGenreList() {
        return genreRepository.findAll();
    }
}
