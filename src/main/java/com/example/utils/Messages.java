package com.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Messages class for retrieving messages from resources.
 * @author Igor A. Zelaya
 */
@Component
public class Messages {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String messageKey){
        return messageSource.getMessage(messageKey, null, Locale.ENGLISH);
    }

}
