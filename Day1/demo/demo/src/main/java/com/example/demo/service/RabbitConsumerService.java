package com.example.demo.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class RabbitConsumerService implements IConsumer{
    @Override
    public void consume() {
        System.out.println("Consume from rabbit");
    }
}
