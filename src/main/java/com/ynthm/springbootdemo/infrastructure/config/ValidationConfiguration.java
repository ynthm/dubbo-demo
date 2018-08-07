package com.ynthm.springbootdemo.infrastructure.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.Validator;


/**
 * Author : Ynthm
 */
@Configuration
public class ValidationConfiguration {


    public ResourceBundleMessageSource getMessageSource() throws Exception {
        ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
        rbms.setDefaultEncoding("UTF-8");
        rbms.setBasenames("i18n/validation/ValidationMessages");
        return rbms;
    }


    public LocalValidatorFactoryBean validator() throws Exception {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(getMessageSource());
        return localValidatorFactoryBean;
    }

    @Bean
    public Validator getValidator()throws Exception {
        return validator();
    }
}
