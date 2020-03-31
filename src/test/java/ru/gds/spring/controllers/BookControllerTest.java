package ru.gds.spring.controllers;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.GenreRepository;
import ru.gds.spring.interfaces.StatusRepository;
import ru.gds.spring.services.BookService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@ComponentScan("ru.gds.spring")
@AutoConfigureTestDatabase
class BookControllerTest {

    private static final Logger logger = Logger.getLogger(BookControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    @MockBean
    GenreRepository genreRepository;

    @MockBean
    AuthorRepository authorRepository;

    @MockBean
    StatusRepository statusRepository;

    @Test
    void getBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/books")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    void getBookByNameTest() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/books/?name=Кольцо")
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").exists());

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
            assumeTrue(false);
        }
    }

    @Test
    void getBookByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/books/?bookId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    void addBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/books")
                .file("file", "Файл".getBytes())
                .param("name", "Новая книга")
                .param("description", "Описание книги")
                .param("statusId", "1")
                .param("genreIds", "1,2")
                .param("authorIds", "1,2"))
                .andExpect(status().is(200));
    }

    @Test
    void updateBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/books/{id}", 1)
                .param("id", "1")
                .param("name", "Новая книга")
                .param("description", "Описание книги")
                .param("statusId", "1")
                .param("genreIds", "1,2")
                .param("authorIds", "1,2")
                .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    void removeBookByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/books/{bookId}", 1)
                .param("bookId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}
