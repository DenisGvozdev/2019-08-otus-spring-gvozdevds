package main.java.ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
class StudentTestServiceImplTest {

    /**
     * Для работы junit теста в application.yml необходимо указать testMode: true
     */
    @Test
    void testStart() {
        try {
            List<Question> questionsAndAnswers = AppConfig.getFileReaderCSVer().prepareData();

            Question question1 = questionsAndAnswers.get(0);
            assertEquals(question1.checkAnswer("я"), true);

            Question question2 = questionsAndAnswers.get(1);
            assertEquals(question2.checkAnswer("люблю"), true);

            Question question3 = questionsAndAnswers.get(2);
            assertEquals(question3.checkAnswer("кушать"), true);

            Question question4 = questionsAndAnswers.get(3);
            assertEquals(question4.checkAnswer("вкусный"), true);

            Question question5 = questionsAndAnswers.get(4);
            assertEquals(question5.checkAnswer("апельсин"), true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
