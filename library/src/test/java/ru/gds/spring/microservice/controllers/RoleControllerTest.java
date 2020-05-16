package ru.gds.spring.microservice.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
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
import ru.gds.spring.microservice.interfaces.RoleRepository;
import ru.gds.spring.microservice.interfaces.RoleService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoleController.class)
@ComponentScan("ru.gds.spring")
@AutoConfigureTestDatabase
@AutoConfigureDataMongo
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RoleService roleService;

    @MockBean
    RoleRepository roleRepository;

    //@ParameterizedTest(name = "#{index} - username=admin, authorities=ROLE_ADMINISTRATION URL={0}")
    @DisplayName("Проверка API: 1 - поиск всех ролей; 2 - поиск ролей по пользователю")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
    @ValueSource(strings = {"/roles", "/roles/?username=admin"})
    void getRoleForAdminTest(String url) throws Exception {
        mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }

    //@Test
    @DisplayName("Проверка API: добавления роли для пользователя admin")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
    void addRoleTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/roles")
                .param("name", "Тестовый"))
                .andExpect(status().is(200));
    }

    //@Test
    @DisplayName("Проверка API: редактирование роли для пользователя admin")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
    void updateRoleTest() throws Exception {
        mockMvc.perform(getBuilderPUT("/roles/1")
                .param("name", "Тестовый"))
                .andExpect(status().is(200));
    }

    //@Test
    @DisplayName("Проверка API: удаление роли для пользователя admin")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMINISTRATION"})
    void removeRoleByIdForAdminTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/roles/1")
                .param("id", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200));
    }

    // ACL отключен, поэтому тест пока не актуален
    // @Test
    @DisplayName("Проверка API: удаление роли для пользователя user")
    @WithMockUser(username = "user", authorities = {"ROLE_BOOKS_READ"})
    void removeRoleByIdForUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/roles/1")
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
