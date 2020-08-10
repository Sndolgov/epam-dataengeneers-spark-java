package com.epam.hw.hw5.service

import java.util

import com.epam.hw.hw5.service.DataConsumer._
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.springframework.stereotype.Service
//import scala.collection.Map


@Service
class DataConsumerKafkaScala(@transient sparkSession: SparkSession, @transient dataFetcher: DataFetcher, @transient sparkContext: SparkContext) extends Serializable with DataConsumer {
  def readData(): Unit = {
    val customers = sparkContext.broadcast(dataFetcher.customers)
    val products = sparkContext.broadcast(dataFetcher.products)

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
    oneColumn.printSchema()

    oneColumn.show()


    oneColumn
      .withColumn(CUSTOMER_ID, getMapValueInt(customers.value, CUSTOMER_ID)(col("data").getItem("clientId")))
      .withColumn(CUSTOMER_NAME, getMapValueString(customers.value, NAME)(col("data").getItem("clientId")))
      .show
  }

  def getMapValueInt[T](m: util.Map[Integer, T], fieldName: String) = udf {
    (key: Int) => {
      getFieldValue(m.get(key), fieldName, classOf[Integer])
    }
  }

  def getMapValueString[T](m: util.Map[Integer, T], fieldName: String) = udf {
    (key: Int) => {
      getFieldValue(m.get(key), fieldName, classOf[String])
    }
  }

  def getFieldValue[T, E](value: T, fieldName: String, returnClass: Class[E]): E = {

    val classType = value.getClass
    val fields = classType.getDeclaredFields;
    for (field <- fields) {
      if (field.getName == fieldName) {
        field.setAccessible(true)
        return field.get(value).asInstanceOf[E]
      }
    }
    ???
  }

}
