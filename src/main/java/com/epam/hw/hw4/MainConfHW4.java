package com.epam.hw.hw4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Evgeny Borisov
 */
@SpringBootApplication
public class MainConfHW4
{
    public static void main(String[] args)
    {
        ConfigurableApplicationContext run = SpringApplication.run(MainConfHW4.class);
        String[] beanDefinitionNames = run.getBeanDefinitionNames();
    }
}
