package ru.gds.spring.microservice.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.gds.spring.microservice.interfaces.StatusRepository;
import ru.gds.spring.microservice.services.StatusService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StatusController.class)
@ComponentScan("ru.gds.spring")
@AutoConfigureTestDatabase
@AutoConfigureDataMongo
public class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StatusService statusService;

    @MockBean
    StatusRepository statusRepository;

    //@ParameterizedTest(name = "#{index} - username=admin, authorities=ROLE_ADMINISTRATION URL={0}")
    @DisplayName("Проверка API: 1 - поиск всех статусов; 2 - поиск статуса по книге")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
    @ValueSource(strings = {"/statuses", "/statuses/?bookId=1"})
    void getStatusForAdminTest(String url) throws Exception {
        mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }

    //@Test
    @DisplayName("Проверка API: добавления статуса для пользователя admin")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
    void addStatusTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/statuses")
                .param("name", "Тестовый"))
                .andExpect(status().is(200));
    }

    //@Test
    @DisplayName("Проверка API: редактирование статуса для пользователя admin")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
    void updateStatusTest() throws Exception {
        mockMvc.perform(getBuilderPUT("/statuses/1")
                .param("name", "Тестовый"))
                .andExpect(status().is(200));
    }

    //@Test
    @DisplayName("Проверка API: удаление статуса для пользователя admin")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
    void removeStatusByIdForAdminTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/statuses/1")
                .param("id", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200));
    }

    // ACL отключен, поэтому тест пока не актуален
    // @Test
    @DisplayName("Проверка API: удаление статуса для пользователя user")
    @WithMockUser(username = "user", authorities = {"ROLE_BOOKS_READ"})
    void removeStatusByIdForUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/statuses/1")
                .param("id", "1")
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
