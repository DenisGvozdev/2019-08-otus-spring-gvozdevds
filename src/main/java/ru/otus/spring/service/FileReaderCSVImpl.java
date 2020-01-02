package ru.otus.spring.service;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exception.NotFilledParameterException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReaderCSVImpl implements FileReader {

    private final String filePath;
    private final String locale;
    private final List<Question> questionsAndAnswers = new ArrayList<>();

    public FileReaderCSVImpl(String filePath, String locale) {
        this.filePath = filePath;
        this.locale = locale;
    }

    @Override
    public List<Question> prepareData() throws Exception {

        InputStream is = Controller.class.getClassLoader().getResourceAsStream(filePath);
        if (is == null) {
            throw new FileNotFoundException("file not found: " + filePath);
        }

        BufferedReader bReader = new BufferedReader(new InputStreamReader(is));

        while (bReader.ready()) {

            String line = bReader.readLine();

            if (line.isEmpty()) {
                continue;
            }

            String[] row = line.split(",");

            if (row.length < 5) {
                throw new NotFilledParameterException(String.format("Row filled incorrect: %s", line));
            }

            if (!locale.equals(row[1])) {
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
                throw new NotFilledParameterException(
                        String.format("One of parameters is empty: " +
                                        "number = %s; question = %s; variants = %s; rightAnswers = %s",
                                number, question, variants, rightAnswers));
            }

            List<String> variantList = new ArrayList<>(Arrays.asList(variants.split(";")));
            List<String> rightAnswerList = new ArrayList<>(Arrays.asList(rightAnswers.split(";")));

            questionsAndAnswers.add(new Question(number, question, variantList, rightAnswerList));
        }

        return questionsAndAnswers;
    }
}
