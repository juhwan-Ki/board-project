package com.kevin.boardproject.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

/*
* 순수 html 파일과 thymeleaf 파일을 나누어서 관리하는 decoupled logic 설정 파일
* */
@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.decoupledLogic());

        return defaultTemplateResolver;
    }


    /**
     * java 16 버전에 정식으로 추가 된 recode를 사용해 불변 객체로 선언
     */
    @ConfigurationProperties("spring.thymeleaf3")
    public record Thymeleaf3Properties (boolean decoupledLogic){}

}