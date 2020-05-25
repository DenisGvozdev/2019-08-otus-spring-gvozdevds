package ru.gds.spring.microservice.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.gds.spring.microservice.interfaces.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@ComponentScan("ru.gds.spring")
@AutoConfigureTestDatabase
@AutoConfigureDataMongo
@ImportAutoConfiguration({
        RibbonAutoConfiguration.class,
        FeignRibbonClientAutoConfiguration.class,
        FeignAutoConfiguration.class})
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    RoleService roleService;

    @MockBean
    UserService userService;

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

    /* ACL пока отключен
    @MockBean
    AclEntryRepository aclEntryRepository;

    @MockBean
    AclObjectIdentityRepository aclObjectIdentityRepository;

    @MockBean
    AclClassRepository aclClassRepository;

    @MockBean
    AclSidRepository aclSidRepository;
     */

    @ParameterizedTest(name = "#{index} - username=admin, authorities=ROLE_ADMINISTRATION URL={0}")
    @DisplayName("Проверка API: 1 - поиск всех книг; 2 - поиск конкретной книги")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
    @ValueSource(strings = {"/books", "/books/?bookId=&name=Кольцо"})
    void getBookForAdminTest(String url) throws Exception {
        mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }


    @Test
    @DisplayName("Проверка API: добавления книги для пользователя admin")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
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
    @DisplayName("Проверка API: редактирование книги для пользователя admin")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
    void updateBookTest() throws Exception {
        mockMvc.perform(getBuilderPUT("/books/1")
                .file("file", "Файл".getBytes())
                .param("id", "1")
                .param("name", "Новая книга")
                .param("description", "Описание книги")
                .param("statusId", "1")
                .param("genreIds", "1,2")
                .param("authorIds", "1,2"))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Проверка API: удаление книги для пользователя admin")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
    void removeBookByIdForAdminTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/books/1")
                .param("bookId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200));
    }

    // ACL отключен, поэтому тест пока не актуален
    // @Test
    @DisplayName("Проверка API: удаление книги для пользователя user")
    @WithMockUser(username = "user", authorities = {"ROLE_BOOKS_READ"})
    void removeBookByIdForUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/books/1")
                .param("bookId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(403));
    }

    private MockMultipartHttpServletRequestBuilder getBuilderPUT(String url) {
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });
        return builder;
    }
}
