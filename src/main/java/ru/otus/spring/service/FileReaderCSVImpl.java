package ru.otus.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ru.otus.spring.domain.Question;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReaderCSVImpl implements FileReaderCSV {

    private final String filePath;
    private List<Question> questionsAndAnswers;

    FileReaderCSVImpl(String filePath) {
        this.filePath = filePath;
        this.questionsAndAnswers = new ArrayList<Question>();
    }

    @Override
    public List<Question> prepareData() {
        CSVReader reader;
        try {
            String[] row;

            reader = new CSVReader(new FileReader(this.filePath));
            int indx = 0;
            while ((row = reader.readNext()) != null) {
                indx += 1;

                if (indx == 1)
                    continue;

                String number = row[0];
                String question = row[1];
                String variants = row[2];
                String rightAnswers = row[3];

                List<String> variantList = new ArrayList<>(Arrays.asList(variants.split(";")));
                List<String> rightAnswerList = new ArrayList<>(Arrays.asList(rightAnswers.split(";")));

                questionsAndAnswers.add(new Question(number, question, variantList, rightAnswerList));
            }
        } catch (IOException | CsvValidationException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Reading file " + filePath + " error: " + e.toString());
        }
        return questionsAndAnswers;
    }
}
