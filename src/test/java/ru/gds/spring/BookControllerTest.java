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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.gds.spring.controllers.BookController;
import ru.gds.spring.interfaces.*;

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

    @MockBean
    UserRepository userRepository;

    @Test
    @WithMockUser(username = "root", authorities = {"ROLE_ADMIN"})
    void getBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    @WithMockUser(username = "root", authorities = {"ROLE_ADMIN"})
    void getBookByNameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/find?name=Кольцо")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("books"));

    }

    @Test
    @WithMockUser(username = "root", authorities = {"ROLE_ADMIN"})
    void addBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/add")
                .file("file", "Файл".getBytes())
                .param("name", "Новая книга")
                .param("description", "Описание книги")
                .param("statusId", "1")
                .param("genreIds", "1,2")
                .param("authorIds", "1,2"))
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    @WithMockUser(username = "root", authorities = {"ROLE_ADMIN"})
    void updateBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/edit")
                .file("file", "Файл".getBytes())
                .param("id", "1")
                .param("name", "Новая книга")
                .param("description", "Описание книги")
                .param("statusId", "1")
                .param("genreIds", "1,2")
                .param("authorIds", "1,2"))
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    @WithMockUser(username = "root", authorities = {"ROLE_ADMIN"})
    void removeBookByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/remove?bookId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    @WithMockUser(username = "root", authorities = {"ROLE_ADMIN"})
    void getInfoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/info")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("operation"));
    }

    @Test
    @WithMockUser(username = "root", authorities = {"ROLE_ADMIN"})
    void getInfoBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/infoBook?bookId=1&mode=show")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("operation"));
    }
}
