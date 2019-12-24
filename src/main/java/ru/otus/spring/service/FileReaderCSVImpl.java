package ru.otus.spring.service;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import ru.otus.spring.config.AppConfig;
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
    private final List<Question> questionsAndAnswers = new ArrayList<>();

    public FileReaderCSVImpl(String filePath) {
        this.filePath = filePath;
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

            MessageServiceImpl ms = AppConfig.getMessageService();

            InputStream is = Controller.class.getClassLoader().getResourceAsStream(this.filePath);
            if (is == null) {
                throw new Exception("file not found: " + this.filePath);
            }

            BufferedReader bReader = new BufferedReader(new InputStreamReader(is));

            while (bReader.ready()) {

                String line = bReader.readLine();
                String[] row = line.split(",");

                if (row.length < 4) {
                    // Указаны не все параметры
                    throw new Exception(ms.getLocalizedMessage("test.error.row", new Object[]{line}));
                }

                String number = row[0];
                String question = ms.getLocalizedMessage(row[1], null);
                String variants = ms.getLocalizedMessage(row[2], null);
                String rightAnswers = ms.getLocalizedMessage(row[3], null);

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
