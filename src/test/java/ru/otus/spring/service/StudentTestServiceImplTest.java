package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class StudentTestServiceImplTest {

    @Autowired
    private FileReader fileReader;

    @Test
    @DisplayName("Test method testStart")
    void testStart() {
        try {
            List<Question> questionsAndAnswers = fileReader.prepareData();

            Question question1 = questionsAndAnswers.get(0);
            assertTrue(question1.checkAnswer("я"));

            Question question2 = questionsAndAnswers.get(1);
            assertTrue(question2.checkAnswer("люблю"));

            Question question3 = questionsAndAnswers.get(2);
            assertTrue(question3.checkAnswer("кушать"));

            Question question4 = questionsAndAnswers.get(3);
            assertTrue(question4.checkAnswer("вкусный"));

            Question question5 = questionsAndAnswers.get(4);
            assertTrue(question5.checkAnswer("апельсин"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
