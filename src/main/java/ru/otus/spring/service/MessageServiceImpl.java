package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import ru.otus.spring.constants.Constant;

import java.util.Locale;

public class MessageServiceImpl implements MessageService {

    private final Locale locale;

    @Autowired
    private MessageSource messageSource;

    public MessageServiceImpl(String locale) {
        this.locale = new Locale(locale);
    }

    public String getLocalizedMessage(String key, Object[] params){
        return messageSource.getMessage(key, params, Constant.DEFAULT, locale);
    }

}
