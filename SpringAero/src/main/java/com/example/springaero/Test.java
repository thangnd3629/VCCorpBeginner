package com.example.springaero;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Test {

    @Value("${db.hostname}")
    private String env;
    @Bean
    public void testBean(){

        System.out.print("=================================");

        System.out.print(env+"\n");
    }
}
