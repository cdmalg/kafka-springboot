package com.kafka.example.test.service;

public interface IKafkaTestService {

  String pullMessage(String message) throws InterruptedException;
}
