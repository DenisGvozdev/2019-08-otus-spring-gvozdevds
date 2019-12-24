package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.domain.Question;

import java.util.List;

/**
 * Основной сервис для проведения тестирования на знание английского языка
 */
@Service
public class StudentTestServiceImpl implements StudentTestService {

    /**
     * Главный метод, запускающий тест
     */
    @Override
    public void testStart() {
        try {
            MessageServiceImpl ms = AppConfig.getMessageService();
            ConsoleServiceImpl cs = AppConfig.getConsoleService();

            // Начитываем вопросы и ответы из файла questions.csv
            List<Question> questionsAndAnswers = AppConfig.getFileReaderCSVer().prepareData();

            // Начинаем работу

            // Пожалуйста введите через пробел Ваше имя и фамилию:
            cs.print(ms.getLocalizedMessage("test.input.fio", null));

            // Здравствуйте
            String hello = ms.getLocalizedMessage("test.input.hello", null);

            // Выберите правильные ответы из предложенных вариантов
            String choose = ms.getLocalizedMessage("test.input.choose", null);

            // верно / не верно
            String right = ms.getLocalizedMessage("test.output.answer.right", null);
            String wrong = ms.getLocalizedMessage("test.output.answer.wrong", null);

            cs.println("\n" + hello + " " + cs.read());
            cs.println(choose);

            int indx = 0;
            int countRightAnswers = 0;
            for (Question itm : questionsAndAnswers) {
                indx += 1;

                // Вопрос {0}: {1} > Варианты: {2} > Ваш ответ:
                cs.print(ms.getLocalizedMessage(
                        "test.input.answer", new Object[]{indx, itm.getQuestion(), itm.getVariants()}));

                boolean answerResult = itm.checkAnswer(cs.read());

                // Ваш ответ {0}
                String answer = ((answerResult) ? " " + right + " " : " " + wrong + " ");
                cs.println(ms.getLocalizedMessage("test.output.result", new Object[]{answer}) + "\n");

                if (answerResult)
                    countRightAnswers += 1;
            }

            // Тест окончен. Спасибо за ответы! Количество правильных ответов {0} из {1}
            cs.println(ms.getLocalizedMessage(
                    "test.output.end", new Object[]{countRightAnswers, questionsAndAnswers.size()}));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
