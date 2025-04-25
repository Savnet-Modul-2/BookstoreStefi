package com.example.SpringBookstore.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class BookstoreAsyncConfigurer implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();

        threadPoolExecutor.setCorePoolSize(5); //a maximum of 5 threads can run in parallel immediately after they are created
        threadPoolExecutor.setMaxPoolSize(10); //a maximum of 10 threads can be created simultaneously
        threadPoolExecutor.setQueueCapacity(50); //a maximum of 50 threads can wait in the queue while other threads are running

        threadPoolExecutor.initialize(); //start the thread pool executor manually

        return threadPoolExecutor;
    }
}
