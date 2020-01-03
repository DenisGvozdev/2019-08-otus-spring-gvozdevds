package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentTestServiceImpl implements StudentTestService {

    private final MessageService ms;
    private final ConsoleService cs;
    private final FileReader fileReader;

    private final List<String> answersHistory = new ArrayList<>();

    public StudentTestServiceImpl(MessageService ms, ConsoleService cs, FileReader fileReader) {
        this.ms = ms;
        this.cs = cs;
        this.fileReader = fileReader;
    }

    @Override
    public void testStart() {
        try {
            // Начитываем вопросы и ответы из файла questions.csv
            List<Question> questionsAndAnswers = fileReader.prepareData();

            cs.print(ms.getLocalizedMessage("test.input.fio"));
            String fio = cs.read();

            cs.println("\n" + ms.getLocalizedMessage("test.input.hello", fio));
            cs.println(ms.getLocalizedMessage("test.input.choose"));

            answersHistory.add(ms.getLocalizedMessage("test.output.welcome"));

            // Задаем все вопросы
            askAllQuestions(questionsAndAnswers);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void askAllQuestions(List<Question> questionsAndAnswers) {

        String right = ms.getLocalizedMessage("test.output.answer.right");
        String wrong = ms.getLocalizedMessage("test.output.answer.wrong");

        int indx = 0;
        int countRightAnswers = 0;
        for (Question itm : questionsAndAnswers) {
            indx += 1;

            // Вопрос {0}: {1} > Варианты: {2} > Ваш ответ:
            String questionAndAnswer = ms.getLocalizedMessage(
                    "test.input.answer", indx, itm.getQuestion(), itm.getVariants());
            cs.print(questionAndAnswer);

            String currentResponse = cs.read();
            boolean answerResult = itm.checkAnswer(currentResponse);

            // Ваш ответ {0}
            String answer = ((answerResult) ? " " + right + " " : " " + wrong + " ");
            questionAndAnswer += currentResponse + " : " + answer;
            cs.println(ms.getLocalizedMessage("test.output.result", answer) + "\n");

            // Запоминаем результат по текущему вопросу
            answersHistory.add(questionAndAnswer);

            if (answerResult)
                countRightAnswers += 1;
        }

        String calculateResults = ms.getLocalizedMessage(
                "test.output.end", countRightAnswers, questionsAndAnswers.size());
        cs.println(calculateResults);
        answersHistory.add(calculateResults);
    }

    public List<String> getAnswersHistory() {
        return answersHistory;
    }
}
