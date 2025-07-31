package com.quangnv.msb.configuration.message;

import com.quangnv.msb.core.funtional.Maybe;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageTranslator {
    private final MessageSource messageSource;

    public MessageTranslator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String key, Object... args) {
        return Maybe.of(key)
                .mapThrowingFunc(k -> messageSource.getMessage(k, args, Locale.getDefault()))
                .orElse(key);
    }
}
