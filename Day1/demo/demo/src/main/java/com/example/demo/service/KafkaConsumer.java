package com.example.demo.service;


import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
@Profile("dev")

public class KafkaConsumer implements IConsumer {
    private class Random{
        public static String genString(){
            return "rand";
        }
    }

    public void consume(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Consuming from datasource kafka: "+ Random.genString());
            }
        }, 0, 1000);

    }

}
