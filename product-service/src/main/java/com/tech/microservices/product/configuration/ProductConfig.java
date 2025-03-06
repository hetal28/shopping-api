package com.tech.microservices.product.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    public ModelMapper modelMapperBean(){
        return new ModelMapper();
    }
}
