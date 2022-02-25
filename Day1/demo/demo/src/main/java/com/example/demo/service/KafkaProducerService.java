package com.example.demo.service;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class KafkaProducerService implements IProducer, CommandLineRunner {

    @Autowired
    Producer<String, String> producer;
    @Override
    public void publishMessage(String topic, String message)  {
                producer.send(new ProducerRecord<String, String>(topic, message));
    }

    @Override
    public void run(String... args) throws Exception {
        while (true){
            publishMessage("multiple-channel", new Date().toString());
            Thread.sleep(100);
        }
    }
}
