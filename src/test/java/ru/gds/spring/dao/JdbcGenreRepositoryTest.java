package ru.gds.spring.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

@JdbcTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/app-config.xml")
@Import(JdbcGenreRepository.class)
public class JdbcGenreRepositoryTest {

    @Autowired
    GenreRepository jdbcGenreRepository;

    @Test
    public void insertGenre() {
        Genre genre = new Genre("Исторический");
        boolean result = jdbcGenreRepository.insert(genre);
        assumeTrue(result);
        System.out.println("Жанр добавлен: " + result);

        List<Genre> genreList = jdbcGenreRepository.getAll();
        System.out.println("Все жанры: " + genreList);
    }

    @Test
    public void updateGenre() {
        Genre genre = jdbcGenreRepository.getById(1);
        genre.setName("Фэнтези+");
        boolean result = jdbcGenreRepository.update(genre);
        assumeTrue(result);
        System.out.println("Жанр обновлен: " + result);

        genre = jdbcGenreRepository.getById(1);
        System.out.println("Новые данные: " + genre.toString());
    }

    @Test
    public void getById() {
        Genre genre = jdbcGenreRepository.getById(1);
        assumeTrue(genre != null);
        assertEquals("Фэнтези", genre.getName());
        System.out.println(genre.getName());
    }

    @Test
    public void getAll() {
        List<Genre> genreList = jdbcGenreRepository.getAll();
        assumeTrue(genreList.size() == 3);
        System.out.println("Количество жанров: " + genreList.size());
    }

    @Test
    public void removeGenre() {
        boolean result = jdbcGenreRepository.removeById(3);
        assumeTrue(result);
        System.out.println("Жанр удален: " + result);

        List<Genre> genreList = jdbcGenreRepository.getAll();
        System.out.println("Все жанры: " + genreList);
    }
}
