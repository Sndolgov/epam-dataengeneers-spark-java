package com.epam;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.Tuple2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Evgeny Borisov
 */
public class NiceServiceTest {

    private static SparkConf conf;
    private static JavaSparkContext sc;
    private NiceService niceService;

    @BeforeClass
    public static void setUpForClass() throws Exception {
        conf = new SparkConf().setAppName("hello").setMaster("local[*]");
        sc = new JavaSparkContext(conf);
    }

    @Before
    public void setUp() throws Exception {
        niceService = new NiceService();
    }

    @Test
    public void countSomething() {



        JavaRDD<String> rdd = sc.parallelize(Stream.of(
                "java",
                "scala",
                "c++",
                "c"

        ).collect(Collectors.toList()));

        long numberOfGoodLines = niceService.countSomething(rdd);
        Assert.assertEquals(2, numberOfGoodLines);


        JavaPairRDD<String, Integer> pairRDD = rdd.mapToPair(s -> new Tuple2<>(s, s.length()));
        JavaDoubleRDD doubleRDD = rdd.mapToDouble(String::length);
        doubleRDD.sum();


    }



}








