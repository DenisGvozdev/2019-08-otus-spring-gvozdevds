package ru.otus.spring.service;

public interface MessageService {

    String getLocalizedMessage(String key, Object[] params);
}
