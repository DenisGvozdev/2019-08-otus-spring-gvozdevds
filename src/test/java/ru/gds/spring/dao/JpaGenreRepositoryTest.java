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
    void fullGenreTest() {
        // Создание
        Genre genre = new Genre("Исторический");
        genre = jpaGenreRepository.save(genre);
        long id = genre.getId();
        boolean result = genre.getId() > 0;
        logger.debug("Жанр добавлен: " + result);
        assumeTrue(result);

        // Поиск всех
        List<Genre> genreList = jpaGenreRepository.findAll();
        logger.debug("Все жанры: " + genreList);

        // Поиск по ID и обновление
        genre = jpaGenreRepository.findById(id);
        genre.setName("Фэнтези+");
        result = jpaGenreRepository.updateById(genre);
        logger.debug("Жанр обновлен: " + result);
        assumeTrue(result);

        genre = jpaGenreRepository.findById(id);
        logger.debug("Новые данные: " + PrintUtils.printObject(null, genre));

        // Удаление
        result = jpaGenreRepository.deleteById(id);
        logger.debug("Жанр удален: " + result);
        assumeTrue(result);

        genreList = jpaGenreRepository.findAll();
        logger.debug("Все жанры: " + genreList);
        assumeTrue(genreList.size() == 3);
    }
}
