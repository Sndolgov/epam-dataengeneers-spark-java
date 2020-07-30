package com.epam.hw.hw4.conf;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.SQLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static com.epam.hw.hw4.Profiles.DEV;
import static com.epam.hw.hw4.Profiles.PROD;
import static java.util.Arrays.asList;

/**
 * @author Evgeny Borisov
 */
@Configuration
public class SparkConfig
{

    @Bean
    public SQLContext sqlContext(SparkContext sc){
        return new SQLContext(sc);
    }

    @Bean
    public JavaSparkContext javaSparkContext(SparkContext sc){
        return new JavaSparkContext(sc);
    }

    @Bean
    public Broadcast<List<String>> garbage(JavaSparkContext sc) {
        List<String> list = asList("in", "the", "a");
        return sc.broadcast(list);
    }

    @Bean
    public SparkContext sc(SparkConf conf) {
        return new SparkContext(conf);
    }

    @Profile(PROD)
    @Primary
    @Bean
    public SparkConf sparkConfProd() {
        return new SparkConf().setAppName("words").setMaster("yarn[*]");
    }

    @Profile(DEV)
    @Bean
    public SparkConf sparkConfDev() {
        return new SparkConf().setAppName("words").setMaster("local[*]");
    }
}



