package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import ru.otus.spring.constants.Constant;

import java.util.Locale;

public class MessageServiceImpl implements MessageService {

    private final Locale locale;
    private final MessageSource messageSource;

    public MessageServiceImpl(String locale, MessageSource messageSource) {
        this.locale = new Locale(locale);
        this.messageSource = messageSource;
    }

    public String getLocalizedMessage(String key, Object[] params) {
        return messageSource.getMessage(key, params, Constant.DEFAULT, locale);
    }

}
