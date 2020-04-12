package ru.gds.spring.controllers;

import com.github.cloudyrock.mongock.Mongock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.gds.spring.interfaces.*;
import ru.gds.spring.services.AuthorService;
import ru.gds.spring.services.BookService;
import ru.gds.spring.services.GenreService;
import ru.gds.spring.services.StatusService;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = BookController.class)
@Import({BookService.class, AuthorService.class, StatusService.class, GenreService.class})
@ComponentScan("ru.gds.spring")
class BookControllerTest {

    @Autowired
    WebTestClient webTestClient;
    @MockBean
    BookReactiveRepository bookReactiveRepository;
    @MockBean
    GenreReactiveRepository genreReactiveRepository;
    @MockBean
    AuthorReactiveRepository authorReactiveRepository;
    @MockBean
    StatusReactiveRepository statusReactiveRepository;
    @MockBean
    Mongock Mongock;

    @Test
    void getBookTest() {
        webTestClient.get()
                .uri("/books")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getBookByNameTest() {
        webTestClient.get()
                .uri("/books/{param}?bookId=&name=Кольцо", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getBookByIdTest() {
        webTestClient.get()
                .uri("/books/{param}?bookId=5e638167d9ab3c3275ce2366&name=", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void addBookTest() {
        webTestClient.post()
                .uri("/books", 1)
                .accept(MediaType.APPLICATION_JSON)
                .body(getRequestBody())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateBookTest() {
        webTestClient.put()
                .uri("/books/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .body(getRequestBody())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void removeBookByIdTest() {
        webTestClient.delete()
                .uri("/books/{bookId}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    private BodyInserters.MultipartInserter getRequestBody() {
        MultipartBodyBuilder body = new MultipartBodyBuilder();
        body.part("id", "");
        body.part("name", "Новая книга");
        body.part("description", "Описание книги");
        body.part("statusId", "1");
        body.part("genreIds", "1");
        body.part("authorIds", "1");
        return BodyInserters.fromMultipartData(body.build());
    }
}
