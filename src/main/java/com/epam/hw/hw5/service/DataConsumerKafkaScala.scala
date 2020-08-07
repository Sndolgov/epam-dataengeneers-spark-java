package com.epam.hw.hw5.service

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.springframework.stereotype.Service


@Service
class DataConsumerKafkaScala(sparkSession: SparkSession, dataFetcher: DataFetcher) {
  def getData():Unit={
    val df = sparkSession.read.format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "bsh18_03")
      .load

    val schema: StructType = StructType(Seq(
      StructField("clientId", IntegerType),
      StructField("productId", IntegerType),
      StructField("cashboxId", IntegerType)
    ))

    val oneColumn = df.select(from_json(col("value").cast(StringType), schema).as("data"))
    oneColumn.printSchema();

//    oneColumn.select(
//      col("data") +: (0 until 2).map(i => col("data")(i).alias(s"col$i")): _*
//    ).show()

//    oneColumn.select(col("data").getItem("clientId").as("x")).show();

    oneColumn.withColumn("clientId", col  ("data").getItem("clientId")).show()



  }
}
