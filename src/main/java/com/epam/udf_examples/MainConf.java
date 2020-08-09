package com.epam.udf_examples;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Evgeny Borisov
 */
@Configuration
@ComponentScan
public class MainConf {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConf.class);
        LinkedInService service = context.getBean(LinkedInService.class);
        Dataset<Row> dataset = context.getBean(SparkSession.class).read().json("data/profiles.json");

        dataset.printSchema();
        service.enrich(dataset).show();
    }
}
