package ru.gds.spring.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

import ru.gds.spring.interfaces.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@ComponentScan("ru.gds.spring")
@AutoConfigureTestDatabase
class BookControllerTest {

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

    @MockBean
    AclEntryRepository aclEntryRepository;

    @MockBean
    AclObjectIdentityRepository aclObjectIdentityRepository;

    @MockBean
    AclClassRepository aclClassRepository;

    @MockBean
    AclSidRepository aclSidRepository;

    @DisplayName("Проверка API: 1 - поиск всех книг; 2 - поиск конкретной книги")
    @WithMockUser(username = "admin", authorities = {"ROLE_WRITE"})
    @ParameterizedTest(name = "#{index} - username=admin, authorities=ROLE_WRITE URL={0}")
    @ValueSource(strings = {"/", "/find?name=Кольцо"})
    void getBookForAdminTest(String url) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("books"));
    }

    @DisplayName("Проверка API: 1 - поиск всех книг; 2 - поиск конкретной книги")
    @WithMockUser(username = "user", authorities = {"ROLE_READ"})
    @ParameterizedTest(name = "#{index} - username=user, authorities=ROLE_READ URL={0}")
    @ValueSource(strings = {"/", "/find?name=Кольцо"})
    void getBookForUserTest(String url) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    @DisplayName("Проверка API: добавления книги для пользователя admin")
    @WithMockUser(username = "root", authorities = {"ROLE_WRITE"})
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
    @DisplayName("Проверка API: редактирование книги для пользователя admin")
    @WithMockUser(username = "admin", authorities = {"ROLE_WRITE"})
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
    @DisplayName("Проверка API: удаление книги для пользователя admin")
    @WithMockUser(username = "admin", authorities = {"ROLE_WRITE"})
    void removeBookByIdForAdminTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/remove?bookId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    @DisplayName("Проверка API: удаление книги для пользователя user")
    @WithMockUser(username = "user", authorities = {"ROLE_READ"})
    void removeBookByIdForUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/remove?bookId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(403));
    }


    @DisplayName("Проверка API: 1 - загрузка списков; 2 - загрузка списков с selected элементами")
    @WithMockUser(username = "admin", authorities = {"ROLE_WRITE"})
    @ParameterizedTest(name = "#{index} - username=user, authorities=ROLE_READ URL={0}")
    @ValueSource(strings = {"/info", "/infoBook?bookId=1&mode=show"})
    void getInfoForAdminTest(String url) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("operation"));
    }

    @DisplayName("Проверка API: 1 - загрузка списков; 2 - загрузка списков с selected элементами")
    @WithMockUser(username = "user", authorities = {"ROLE_READ"})
    @ParameterizedTest(name = "#{index} - username=user, authorities=ROLE_READ URL={0}")
    @ValueSource(strings = {"/info", "/infoBook?bookId=1&mode=show"})
    void getInfoForUserTest(String url) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(403));
    }
}
