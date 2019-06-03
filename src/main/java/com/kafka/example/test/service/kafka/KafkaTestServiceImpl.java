package com.kafka.example.test.service.kafka;

import com.kafka.example.test.config.AsyncTask;
import com.kafka.example.test.service.IKafkaTestService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service("kafkaService")
@Slf4j
public class KafkaTestServiceImpl implements IKafkaTestService {

  @Resource
  KafkaTemplate kafkaTemplate;
  @Resource
  AsyncTask task;

  @Override
  public String pullMessage(String message) throws InterruptedException {


    ListenableFuture future = kafkaTemplate.send("pressureTest", message);
    future.addCallback(o                                          -> {
          System.out.println("send-消息发送成功：" + message);},
        throwable -> {
          System.out.println("消息发送失败：" + message);
        });
//    for(int i = 1; i<= 500; i++){
//      task.executeAysncTask(i);
//    }
    return "success";
  }



}
