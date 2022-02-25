package com.example.demo.service;

public interface IProducer {
    void publishMessage(String topic, String message) throws InterruptedException;
}
