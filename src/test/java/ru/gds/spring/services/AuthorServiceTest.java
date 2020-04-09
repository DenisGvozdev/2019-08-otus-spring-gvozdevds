package ru.gds.spring.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.gds.spring.domain.Author;
import ru.gds.spring.interfaces.AuthorReactiveRepository;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class AuthorServiceTest {

    @Autowired
    AuthorService authorService;

    @Mock
    AuthorReactiveRepository authorReactiveRepository;

    @Test
    void insertAuthorTest() {
        Mono<Author> author = authorService.save(new Author("Иванов", "Иван", "Иванович", new Date()));
        StepVerifier
                .create(author)
                .assertNext(obj -> assertNotNull(obj.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateAuthorTest() {
        Author author = getAuthorByName("Перумов");
        author.setThirdName("Перумов обновленный");
        Mono<Author> authorUpd = authorService.save(author);
        StepVerifier
                .create(authorUpd)
                .assertNext(obj -> assertEquals("Перумов обновленный", obj.getThirdName()))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteAuthorTest() {
        Author author = getAuthorByName("Временный");
        when(authorReactiveRepository.findById(author.getId())).thenReturn(Mono.just(author));
        when(authorReactiveRepository.delete(author)).thenReturn(Mono.empty());
        Mono<Void> actual = authorService.deleteById(author.getId());
        StepVerifier
                .create(actual)
                .verifyComplete();
    }

    private Author getAuthorByName(String name) {
        return authorService.findAllByThirdName(name).blockFirst();
    }
}
