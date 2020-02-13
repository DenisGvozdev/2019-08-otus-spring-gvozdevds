package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreRepository;
import ru.gds.spring.util.PrintUtils;

import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataJpaTest
@Import(JpaGenreRepository.class)
class JpaGenreRepositoryTest {

    @Autowired
    GenreRepository jpaGenreRepository;

    private static final Logger logger = Logger.getLogger(JpaGenreRepositoryTest.class);

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

        String name = "Обновленный жанр";
        genre.setName(name);
        boolean result = jpaGenreRepository.updateById(genre);
        logger.debug("Жанр обновлен: " + result);
        assumeTrue(result);

        genre = jpaGenreRepository.findById(genre.getId());
        logger.debug("Новые данные: " + PrintUtils.printObject(null, genre));
        assumeTrue(name.equals(genre.getName()));
    }

    @Test
    void deleteGenreTest() {

        List<Genre> genreList = jpaGenreRepository.findAll();
        assumeTrue(genreList.size() == 3);
        Genre genre = genreList.get(1);

        boolean result = jpaGenreRepository.deleteById(genre.getId());
        logger.debug("Жанр удален: " + result);
        assumeTrue(result);

        genreList = jpaGenreRepository.findAll();
        logger.debug("Все жанры: " + genreList);
        assumeTrue(genreList.size() == 2);
    }
}
