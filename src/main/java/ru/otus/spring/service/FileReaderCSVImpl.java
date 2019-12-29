package ru.otus.spring.service;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Сервис для чтения questions.csv
 */
public class FileReaderCSVImpl implements FileReader {

    private final String filePath;
    private final String locale;
    private final MessageService ms;
    private final List<Question> questionsAndAnswers = new ArrayList<>();


    public FileReaderCSVImpl(String filePath, String locale, MessageService ms) {
        this.filePath = filePath;
        this.locale = locale;
        this.ms = ms;
    }

    /**
     * Читаем ресурс questions.csv
     *
     * @return List<Question> список вопросов для тестирования
     * @throws Exception прокидываем выше
     */
    @Override
    public List<Question> prepareData() throws Exception {
        try {

            InputStream is = Controller.class.getClassLoader().getResourceAsStream(this.filePath);
            if (is == null) {
                throw new Exception("file not found: " + this.filePath);
            }

            BufferedReader bReader = new BufferedReader(new InputStreamReader(is));

            while (bReader.ready()) {

                String line = bReader.readLine();

                if (line.isEmpty()) {
                    continue;
                }

                String[] row = line.split(",");

                if (row.length < 5) {
                    // Указаны не все параметры
                    throw new Exception(ms.getLocalizedMessage("test.error.row", new Object[]{line}));
                }

                if (!locale.equals(row[1])) {
                    // этот вопрос не для текущей локали
                    continue;
                }

                String number = row[0];
                String question = row[2];
                String variants = row[3];
                String rightAnswers = row[4];

                if (StringUtils.isEmpty(number)
                        || StringUtils.isEmpty(question)
                        || StringUtils.isEmpty(variants)
                        || StringUtils.isEmpty(rightAnswers)) {
                    // Один из параметров пустой
                    throw new Exception(ms.getLocalizedMessage("test.error.row", new Object[]{line}));
                }

                List<String> variantList = new ArrayList<>(Arrays.asList(variants.split(";")));
                List<String> rightAnswerList = new ArrayList<>(Arrays.asList(rightAnswers.split(";")));

                questionsAndAnswers.add(new Question(number, question, variantList, rightAnswerList));
            }

        } catch (Exception e) {
            throw new Exception("Reading file: " + filePath + " error: " + Arrays.asList(e.getStackTrace()));
        }
        return questionsAndAnswers;
    }
}
