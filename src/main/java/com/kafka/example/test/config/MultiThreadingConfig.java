package com.kafka.example.test.config;

import java.util.concurrent.Executor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan("com.kafka.example.test.service")
@EnableAsync
public class MultiThreadingConfig implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(500);
    taskExecutor.setMaxPoolSize(600);
    taskExecutor.setQueueCapacity(25);
    taskExecutor.initialize();
    return taskExecutor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
  }
}
