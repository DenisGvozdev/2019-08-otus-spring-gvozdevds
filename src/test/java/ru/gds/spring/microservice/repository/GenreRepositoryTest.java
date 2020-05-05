package ru.gds.spring.microservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import ru.gds.spring.microservice.domain.Genre;
import ru.gds.spring.microservice.interfaces.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
@AutoConfigureTestDatabase
class GenreRepositoryTest {

    @Autowired
    GenreRepository genreRepository;

    @Test
    void insertGenreTest() {
        Genre genre = new Genre("Исторический");
        genre = genreRepository.save(genre);
        assumeTrue(genre.getId() != null);
    }

    @Test
    void updateGenreTest() {
        Genre genre = getGenreByName("Приключения");
        assumeTrue(genre != null);

        String genreName = "Приключения обновление";
        genre.setName(genreName);
        genre = genreRepository.save(genre);
        assumeTrue(genreName.equals(genre.getName()));
    }

    @Test
    void deleteGenreTest() {
        Genre genre = getGenreByName("Временный");
        assumeTrue(genre != null);

        genreRepository.deleteById(genre.getId());

        genre = getGenreByName("Временный");
        assumeTrue(genre == null);
    }

    private Genre getGenreByName(String name) {
        List<String> nameList = new ArrayList<>();
        nameList.add(name);
        List<Genre> genreList = genreRepository.findAllByName(nameList, null);
        return (!genreList.isEmpty()) ? genreList.get(0) : null;
    }
}
