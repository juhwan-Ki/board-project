package com.kevin.boardproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    // Insert or Update 시 Auditing을 통해 사용자의 정보를 가져와서 createBy or ModifiedBy에 자동으로 사용자 정보를 넣어의
    @Bean
    public AuditorAware<String> auditorAware () {
        return () -> Optional.of("kevin"); // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 때 수정해야함
    }
}
