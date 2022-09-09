package com.xpand.challenge.config;

import com.xpand.challenge.dto.MovieDTO;
import com.xpand.challenge.validator.Validator;
import com.xpand.challenge.validator.impl.MovieValidator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    
    @Bean
    Validator<MovieDTO> movieValidator() {
        return new MovieValidator();
    }

}
