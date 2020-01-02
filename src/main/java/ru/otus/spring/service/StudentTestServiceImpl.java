package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.List;

@Service
public class StudentTestServiceImpl implements StudentTestService {

    private final MessageService ms;
    private final ConsoleService cs;
    private final FileReader fileReader;

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

            cs.print(ms.getLocalizedMessage("test.input.fio", null));

            String hello = ms.getLocalizedMessage("test.input.hello", null);
            String choose = ms.getLocalizedMessage("test.input.choose", null);

            cs.println("\n" + hello + " " + cs.read());
            cs.println(choose);

            // Задаем все вопросы
            askAllQuestions(questionsAndAnswers);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void askAllQuestions(List<Question> questionsAndAnswers) {

        String right = ms.getLocalizedMessage("test.output.answer.right", null);
        String wrong = ms.getLocalizedMessage("test.output.answer.wrong", null);

        int indx = 0;
        int countRightAnswers = 0;
        for (Question itm : questionsAndAnswers) {
            indx += 1;

            cs.print(ms.getLocalizedMessage(
                    "test.input.answer", new Object[]{indx, itm.getQuestion(), itm.getVariants()}));

            boolean answerResult = itm.checkAnswer(cs.read());

            String answer = ((answerResult) ? " " + right + " " : " " + wrong + " ");
            cs.println(ms.getLocalizedMessage("test.output.result", new Object[]{answer}) + "\n");

            if (answerResult)
                countRightAnswers += 1;
        }

        cs.println(ms.getLocalizedMessage(
                "test.output.end", new Object[]{countRightAnswers, questionsAndAnswers.size()}));
    }
}
