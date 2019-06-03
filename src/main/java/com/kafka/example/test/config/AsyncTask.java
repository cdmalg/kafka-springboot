package com.kafka.example.test.config;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@Slf4j
public class AsyncTask {

  @Resource
  private KafkaTemplate kafkaTemplate;

  @Async
  public void executeAysncTask(Integer i) throws InterruptedException {
    String message = "";
    for (int j = 0; j < 3000000; j++) {
      message = "蒋帅帅牌压力测试工具第===>" + i + "<===波," + "第===>" + j + "<===次";
      ListenableFuture future = kafkaTemplate.send("pressureTest", message);
//      Thread.sleep(100);
      log.info("Kafka压力测试线程" + i + " ==> : 执行异步任务{} ", j);
    }
  }
}
