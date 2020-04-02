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
import ru.gds.spring.interfaces.AuthorRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    AuthorRepository authorRepository;

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
        Mono<Author> actual = authorService.deleteById(author.getId());
        StepVerifier
                .create(actual)
                .expectNext(author)
                .verifyComplete();
    }

    private Author getAuthorByName(String name) {
        List<String> nameList = new ArrayList<>();
        nameList.add(name);
        List<Author> authorList = authorService.findAllByName(nameList, null);
        return authorList.get(0);
    }
}
