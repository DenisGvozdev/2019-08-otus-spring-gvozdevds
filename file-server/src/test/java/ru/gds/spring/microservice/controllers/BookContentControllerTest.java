package ru.gds.spring.microservice.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookContentController.class)
@ComponentScan("ru.gds.spring")
@AutoConfigureTestDatabase
@AutoConfigureDataMongo
class BookContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Проверка API: постраничная загрузка книги")
    void getBookContentTest() throws Exception {
        mockMvc.perform(get("/content/bookId/pageStart/countPages?bookId=1&pageStart=1&countPages=50")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Проверка API: загрузка файла книги")
    void getFileBookContentTest() throws Exception {
        mockMvc.perform(get("/content/bookId?bookId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Проверка API: добавления содержимого книги")
    void addBookContentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/content/bookId/bookName/type/file")
                .file("file", "Файл".getBytes())
                .param("bookId", "1")
                .param("bookName", "1")
                .param("type", "content"))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Проверка API: удаление содержимого книги")
    void removeBookContentByBookIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/content/bookId")
                .param("bookId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200));
    }
}