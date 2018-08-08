package com.ynthm.springbootdemo.infrastructure.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * Author : Ynthm
 */
public class InternationalizedException extends RuntimeException implements MessageSourceResolvable {
    private static final Locale DEFAULT_LOCALE = Locale.CHINA;

    private final String errorCode;
    private final String[] codes;
    private final Object[] arguments;

    public InternationalizedException(String errorCode, Object... objects) {
        this(null, errorCode, null, objects);
    }

    public InternationalizedException(Throwable cause, String errorCode, Object... objects) {
        this(cause, errorCode, null, objects);
    }

    public InternationalizedException(String errorCode, String defaultMessage, Object... objects) {
        this(null, errorCode, defaultMessage, objects);
    }

    public InternationalizedException(Throwable cause, String errorCode, String defaultMessage, Object... objects) {
        super(errorCode == null ? defaultMessage : errorCode  , cause);
        this.errorCode = errorCode;
        this.codes = new String[]{errorCode};
        this.arguments = objects;
    }

    @Override
    public String[] getCodes() {
        return this.codes;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public String getDefaultMessage() {
        return this.getMessage();
    }


    // 延用getLocalizedMessage的名字，但不是override，不会影响toString()的输出，只是类似的名字表达相似的功能。
    public String getLocalizedMessage(MessageSource messageSource) {
        return getLocalizedMessage(messageSource, this.getLocale());
    }

    public String getLocalizedMessage(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage(this, locale);
    }

    protected Locale getLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        return locale == null ? InternationalizedException.DEFAULT_LOCALE : locale;
    }
}
