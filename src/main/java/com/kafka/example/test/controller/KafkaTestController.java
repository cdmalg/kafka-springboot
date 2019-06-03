package com.kafka.example.test.controller;


import com.kafka.example.test.service.IKafkaTestService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaTestController {

  @Resource
  IKafkaTestService kafkaTestService;

  @GetMapping("/test")
  public String test(){
    return "hello kafka";
  }

  @GetMapping("/pull")
  public String pullMessage(String message) throws InterruptedException {
    return kafkaTestService.pullMessage(message);
  }

}
