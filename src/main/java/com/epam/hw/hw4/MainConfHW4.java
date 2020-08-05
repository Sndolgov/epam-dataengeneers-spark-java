package com.epam.hw.hw4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Evgeny Borisov
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)

//@Configuration
//@ComponentScan
public class MainConfHW4
{
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(MainConfHW4.class);

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfHW4.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
    }
}
