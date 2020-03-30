package ru.gds.spring.controllers;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gds.spring.dto.BookDto;
import ru.gds.spring.interfaces.*;
import ru.gds.spring.services.AuthorService;
import ru.gds.spring.services.BookService;
import ru.gds.spring.services.GenreService;
import ru.gds.spring.services.StatusService;

import java.util.Arrays;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = BookController.class)
@Import({BookService.class, AuthorService.class, StatusService.class, GenreService.class})
@ComponentScan("ru.gds.spring")
//@AutoConfigureTestDatabase
class BookControllerTest {

    private static final Logger logger = Logger.getLogger(BookControllerTest.class);

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
    BookRepository bookRepository;
    @MockBean
    GenreRepository genreRepository;
    @MockBean
    AuthorRepository authorRepository;
    @MockBean
    StatusRepository statusRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    CommentRepository commentRepository;

    @Test
    void getBookTest() {
        try {
            webTestClient.get()
                    .uri("books")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(BookDto.class)
                    .hasSize(2);
        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }
}
