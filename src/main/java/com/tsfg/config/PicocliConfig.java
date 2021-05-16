package com.tsfg.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.spring.PicocliSpringFactory;

@Configuration
public class PicocliConfig {
    private final ApplicationContext applicationContext;

    public PicocliConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public PicocliSpringFactory iFactory() {
        return new PicocliSpringFactory(applicationContext);
    }
}
