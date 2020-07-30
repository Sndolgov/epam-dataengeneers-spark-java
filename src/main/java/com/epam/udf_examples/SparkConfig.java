package com.epam.udf_examples;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Evgeny Borisov
 */
@Configuration
public class SparkConfig {



    @Bean
    public RateScalaUdf calculatorUdf(){
        RateScalaUdf udf = new RateScalaUdf();

        sparkSession().sqlContext().udf().register(udf.getClass().getName(), udf, DataTypes.IntegerType);

        return udf;
    }


    @Bean
    public SparkSession sparkSession(){
        SparkSession session = SparkSession.builder().master("local[*]").appName("linkedin").getOrCreate();
        session.sparkContext().setLogLevel("ERROR");
        return session;
    }

    @Bean
    public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkSession().sparkContext());
    }


    @Bean
    public Broadcast<Map<String, Integer>> techRateMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("spring", 5);
        map.put("spark", 5);
        map.put("groovy", 5);
        map.put("hadoop", 2);
        map.put("yarn", 1);
        return javaSparkContext().broadcast(map);
    }
}
