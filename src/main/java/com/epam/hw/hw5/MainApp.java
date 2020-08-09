package com.epam.hw.hw5;

import com.epam.hw.hw5.service.DataConsumerKafkaJava;
import com.epam.hw.hw5.service.DataConsumerKafkaScala;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApp
{
    public static void main(String[] args) throws InterruptedException
    {
        ConfigurableApplicationContext context = SpringApplication.run(MainApp.class);
        DataConsumerKafkaJava kafkaJava = context.getBean(DataConsumerKafkaJava.class);
        DataConsumerKafkaScala kafkaScala = context.getBean(DataConsumerKafkaScala.class);
//        kafkaJava.readData();
        kafkaScala.readData();



//        Thread.sleep(200000000);
    }
}
