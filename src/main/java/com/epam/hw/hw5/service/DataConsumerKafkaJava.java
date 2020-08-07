package com.epam.hw.hw5.service;

import lombok.SneakyThrows;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DataConsumerKafkaJava
{
    @Autowired
    private SparkSession sparkSession;

    @SneakyThrows
    public void readData(){
     /*   Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092,anotherhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "use_a_separate_group_id_for_each_stream");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Arrays.asList("topicA", "topicB");

        InputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(
                streamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
        );*/


        Dataset<Row> df = sparkSession
                .read()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "bsh18_03")
                .load();

//        StructType schema = StructType(Seq(
//                StructField("clientId", IntegerType),
//                StructField("productId", IntegerType),
//                StructField("cashboxId", IntegerType)
//        ));
//
//        df.select(from_json(col("value").cast(StringType), schema).as("data")).show();

    }
}
