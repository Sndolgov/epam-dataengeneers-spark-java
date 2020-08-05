package com.epam.hw.hw4.conf;


/**
 * @author Evgeny Borisov
 */
//@Configuration
public class SparkConfig
{
/*
    public static final String PROD = "PROD";
    public static final String DEV = "DEV";

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
    }*/
}



