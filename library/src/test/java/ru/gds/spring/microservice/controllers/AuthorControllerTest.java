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
import ru.gds.spring.microservice.interfaces.AuthorRepository;
import ru.gds.spring.microservice.interfaces.AuthorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
@ComponentScan("ru.gds.spring")
@AutoConfigureTestDatabase
@AutoConfigureDataMongo
@ImportAutoConfiguration({
        RibbonAutoConfiguration.class,
        FeignRibbonClientAutoConfiguration.class,
        FeignAutoConfiguration.class})
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthorService authorService;

    @MockBean
    AuthorRepository authorRepository;

//    @ParameterizedTest(name = "#{index} - username=admin, authorities=ROLE_ADMINISTRATION URL={0}")
//    @DisplayName("Проверка API: 1 - поиск всех авторов; 2 - поиск авторов по книге")
//    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
//    @ValueSource(strings = {"/authors", "/authors/?bookId=1"})
//    void getAuthorForAdminTest(String url) throws Exception {
//        mockMvc.perform(get(url)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("$.length()").isNotEmpty());
//    }
//
//    @Test
//    @DisplayName("Проверка API: добавления автора для пользователя admin")
//    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
//    void addAuthorTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/authors")
//                .param("firstName", "Плотников")
//                .param("secondName", "Павел")
//                .param("thirdName", "Оплотович")
//                .param("fio", "")
//                .param("birthDate", "01.01.1985"))
//                .andExpect(status().is(200));
//    }
//
//    @Test
//    @DisplayName("Проверка API: редактирование автора для пользователя admin")
//    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
//    void updateAuthorTest() throws Exception {
//        mockMvc.perform(getBuilderPUT("/authors/1")
//                .param("firstName", "Плотников")
//                .param("secondName", "Павел")
//                .param("thirdName", "Оплотович")
//                .param("fio", "")
//                .param("birthDate", "02.02.1985"))
//                .andExpect(status().is(200));
//    }
//
//    @Test
//    @DisplayName("Проверка API: удаление автора для пользователя admin")
//    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
//    void removeAuthorByIdForAdminTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                .delete("/authors/1")
//                .param("id", "1")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().is(200));
//    }
//
//    // ACL отключен, поэтому тест пока не актуален
//    // @Test
//    @DisplayName("Проверка API: удаление автора для пользователя user")
//    @WithMockUser(username = "user", authorities = {"ROLE_BOOKS_READ"})
//    void removeAuthorByIdForUserTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                .delete("/authors/1")
//                .param("id", "1")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().is(403));
//    }
//
//    private MockMultipartHttpServletRequestBuilder getBuilderPUT(String url) {
//        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url);
//        builder.with(new RequestPostProcessor() {
//            @Override
//            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
//                request.setMethod("PUT");
//                return request;
//            }
//        });
//        return builder;
//    }
}
