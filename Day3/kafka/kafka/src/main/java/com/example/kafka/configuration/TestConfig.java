package com.example.kafka.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TestConfig {
    @Bean
    public List<Integer> b1(){
        List<Integer> l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);
        return l;
    }
    @Bean
    public String b2(){
        List l = b1();
        b1().remove(0);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        return "OK";
    }
    @Bean
    public String b3(){
        System.out.println(b1());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        return "ok b3";
    }
}
