package ru.gds.spring.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreReactiveRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class GenreServiceTest {

    @Autowired
    GenreService genreService;

    @Mock
    GenreReactiveRepository genreReactiveRepository;

    @Test
    void insertGenreTest() {
        Mono<Genre> genre = genreService.save(new Genre("Новый"));
        StepVerifier
                .create(genre)
                .assertNext(obj -> assertNotNull(obj.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateGenreTest() {
        Genre genre = getGenreByName("Фэнтези");
        genre.setName("Фэнтези обновленный");
        Mono<Genre> genreUpd = genreService.save(genre);
        StepVerifier
                .create(genreUpd)
                .assertNext(obj -> assertEquals("Фэнтези обновленный", obj.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteGenreTest() {
        Genre genre = getGenreByName("temporary");
        when(genreReactiveRepository.findById(genre.getId())).thenReturn(Mono.just(genre));
        when(genreReactiveRepository.delete(genre)).thenReturn(Mono.empty());
        Mono<Void> actual = genreService.deleteById(genre.getId());
        StepVerifier
                .create(actual)
                .verifyComplete();
    }

    private Genre getGenreByName(String name) {
        return genreService.findAllByName(name).blockFirst();
    }
}