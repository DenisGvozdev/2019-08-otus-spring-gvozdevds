package ru.gds.spring;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gds.spring.controllers.BookController;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.GenreRepository;
import ru.gds.spring.interfaces.StatusRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(BookController.class)
@Import({BookRepository.class,
        AuthorRepository.class,
        GenreRepository.class,
        StatusRepository.class})
@ComponentScan("ru.gds.spring")
public class BookControllerTest {

    private static final Logger logger = Logger.getLogger(BookRepositoryTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookController)
                .build();
    }

    @Test
    public void getBookTest() {
        try {
            System.out.println("hello");
            mockMvc.perform(get("/"))
                    .andExpect(model().attribute("books", hasSize(2)));

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
