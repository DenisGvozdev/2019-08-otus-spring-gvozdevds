package ru.gds.spring.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.BookReactiveRepository;
import ru.gds.spring.params.ParamsBook;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class BookServiceTest {

    @Autowired
    BookService bookService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Mock
    BookReactiveRepository bookReactiveRepository;

    @Test
    void insertBookTest() {
        Mono<Book> book = bookService.save(
                new ParamsBook("999", "Мастер и Маргарита", "Классика", null,
                        getGenreIdByName("Фэнтези"), getAuthorIdByName("Перумов"), getStatusIdByName("active")));

        StepVerifier
                .create(book)
                .assertNext(obj -> assertNotNull(obj.getId()))
                .expectComplete()
                .log();
    }

    @Test
    void updateBookTest() {
        Book bookOld = getBookByName("Кольцо тьмы");
        Mono<Book> book = bookService.save(
                new ParamsBook(bookOld.getId(), "Кольцо тьмы обновление", "увлекательная книга", null,
                        getGenreIdByName("Фэнтези"), getAuthorIdByName("Перумов"), getStatusIdByName("active")));

        StepVerifier
                .create(book)
                .assertNext(obj -> assertNotNull(obj.getId()))
                .expectComplete()
                .log();
    }

    @Test
    void deleteBookTest() {
        Book book = getBookByName("Робинзон Крузо");
        when(bookReactiveRepository.findById(book.getId())).thenReturn(Mono.just(book));
        when(bookReactiveRepository.delete(book)).thenReturn(Mono.empty());
        Mono<Void> actual = bookService.deleteById(book.getId());
        StepVerifier
                .create(actual)
                .verifyComplete();
    }

    private Book getBookByName(String name) {
        return bookService.findAllByName(name).blockFirst();
    }

    private String getAuthorIdByName(String thirdName) {
        Author author = mongoTemplate.findOne(query(where("thirdName").is(thirdName)), Author.class);
        return (author != null) ? author.getId() : null;
    }

    private String getGenreIdByName(String name) {
        Genre genre = mongoTemplate.findOne(query(where("name").is(name)), Genre.class);
        return (genre != null) ? genre.getId() : null;
    }

    private String getStatusIdByName(String name) {
        Status status = mongoTemplate.findOne(query(where("name").is(name)), Status.class);
        return (status != null) ? status.getId() : null;
    }
}