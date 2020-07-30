package com.epam.ilya.conf;

import com.epam.ilya.MainConf;
import com.epam.ilya.services.TopWordsService;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import scala.collection.immutable.List;

import java.util.Arrays;

import static com.epam.ilya.conf.SparkConfig.DEV;
import static java.util.Arrays.asList;

/**
 * @author Evgeny Borisov
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainConf.class)
@ActiveProfiles(DEV)
public class TopWordsServiceTest
{


    @Autowired
    private SQLContext sqlContext;


    @Test
    public void sql()
    {

        SparkSession sparkSession = SparkSession.builder().appName("").master("local").getOrCreate();
        Dataset<Row> dataframe = sqlContext.read().json("data/profiles.json");
        dataframe.filter(functions.col("age").geq(18))
                .withColumn("keywords_size*2", functions.size(functions.col("keywords")).multiply(2))
                .show();

        Arrays.asList(dataframe.dtypes()).forEach(System.out::println);


//        dataframe.printSchema();
//        dataframe.show();

    }

    @Autowired
    private TopWordsService topWordsService;

    @Autowired
    private JavaSparkContext sc;


    @Test
    public void topWords()
    {


        JavaRDD<String> rdd = sc.parallelize(asList("java in in the a a a a ",
                "scala scala in in the a a a a "));
        List<String> list = topWordsService.topX(rdd.rdd(), 1);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("scala", list.iterator().next());
    }
}






