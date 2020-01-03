package com.ynthm.springbootdemo.infrastructure.config;


import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;


/**
 * Author : Ynthm
 */
@Configuration
public class ValidationConfiguration {
    @Bean
    public Validator getValidator() {
        // 默认在 resources 根目录下 ValidationMessages
        Validator validator = Validation.byDefaultProvider().
                configure().
                messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("i18n/ValidationMessages"))).
                buildValidatorFactory().getValidator();
        return validator;
    }


//    public ResourceBundleMessageSource getMessageSource() throws Exception {
//        ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
//        rbms.setDefaultEncoding("UTF-8");
//        rbms.setBasenames("i18n/validation/ValidationMessages");
//        return rbms;
//    }
//
//
//    public LocalValidatorFactoryBean validator() throws Exception {
//        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
//        localValidatorFactoryBean.setValidationMessageSource(getMessageSource());
//        return localValidatorFactoryBean;
//    }

}
