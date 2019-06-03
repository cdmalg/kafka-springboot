package com.kafka.example.test.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestKafkaListener {

  @KafkaListener(topics = {"HelloKafka","testKafka"})
  public void receive(String message){
    log.info("===============================Kafka消息监听："+message);
  }

}
