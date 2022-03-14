package com.example.demo.service;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
@Profile("dev")

public class KafkaConsumerService implements IConsumer {
    private class Random{
        public String genString(){
            return "rand";
        }
    }

    public void consume(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Consuming from datasource kafka: "+ new Random().genString());
            }
        }, 0, 1000);

    }

}
