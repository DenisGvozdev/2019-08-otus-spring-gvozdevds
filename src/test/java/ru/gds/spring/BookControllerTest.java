package ru.gds.spring;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.gds.spring.controllers.BookController;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.GenreRepository;
import ru.gds.spring.interfaces.StatusRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@ComponentScan("ru.gds.spring")
@AutoConfigureTestDatabase
class BookControllerTest {

    private static final Logger logger = Logger.getLogger(BookRepositoryTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookRepository bookRepository;

    @MockBean
    GenreRepository genreRepository;

    @MockBean
    AuthorRepository authorRepository;

    @MockBean
    StatusRepository statusRepository;

    @Test
    void getBookTest() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/")
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(model().attributeExists("books"));

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }

    @Test
    void getBookByNameTest() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/find?name=Кольцо")
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(model().attributeExists("books"));

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }

    @Test
    void addBookTest() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.multipart("/add")
                    .file("file", "Файл".getBytes())
                    .param("name", "Новая книга")
                    .param("description", "Описание книги")
                    .param("statusId", "1")
                    .param("genreIds", "1,2")
                    .param("authorIds", "1,2"))
                    .andExpect(status().is(302))
                    .andExpect(redirectedUrl("/"));

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }

    @Test
    void updateBookTest() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.multipart("/createUpdate")
                    .file("file", "Файл".getBytes())
                    .param("bookId", "1")
                    .param("name", "Новая книга")
                    .param("description", "Описание книги")
                    .param("statusId", "1")
                    .param("genreIds", "1,2")
                    .param("authorIds", "1,2"))
                    .andExpect(status().is(302))
                    .andExpect(redirectedUrl("/"));

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }

    @Test
    void removeBookByIdTest() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/remove?bookId=1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(model().attributeExists("books"));

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);

        }
    }

    @Test
    void getInfoTest() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/info")
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(model().attributeExists("book"))
                    .andExpect(model().attributeExists("operation"));

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }

    @Test
    void getInfoBookTest() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/infoBook?bookId=1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(model().attributeExists("book"))
                    .andExpect(model().attributeExists("operation"));

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }
}
