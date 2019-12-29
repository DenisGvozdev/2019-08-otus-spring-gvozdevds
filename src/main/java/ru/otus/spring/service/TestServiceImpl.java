package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private FileReaderCSVImpl fileReaderCSVer;

    public TestServiceImpl(FileReaderCSVImpl fileReaderCSVer) {
        this.fileReaderCSVer = fileReaderCSVer;
    }

    @Override
    public void testStart() {
        try {
            // Начитываем вопросы и ответы из файла questions.csv
            List<Question> questionsAndAnswers = this.fileReaderCSVer.prepareData();

            // Начинаем работу
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Пожалуйста введите через пробел Ваше имя и фамилию:");
            String fio = br.readLine();

            System.out.println("\nЗдравствуйте " + fio);
            System.out.println("Выберите правильные ответы из предложенных вариантов");

            int indx = 0;
            int countRightAnswers = 0;
            for (Question itm : questionsAndAnswers) {
                indx += 1;
                System.out.printf("Вопрос %d: %s > Варианты: %s > Ваш ответ: \n", indx, itm.getQuestion(), itm.getVariants());

                String input = br.readLine();
                boolean answerResult = itm.checkAnswer(input);
                System.out.println("Вы ответили: " + ((answerResult) ? " верно " : " не верно"));

                if (answerResult)
                    countRightAnswers += 1;
            }

            System.out.printf("Тест окончен. Спасибо за ответы! Количество правильных ответов %d из %d \n",
                    countRightAnswers, questionsAndAnswers.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
