package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface FileReaderCSV {

    public List<Question> prepareData();

}
