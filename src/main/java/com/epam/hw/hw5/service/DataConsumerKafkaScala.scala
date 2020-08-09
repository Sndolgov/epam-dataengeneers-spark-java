package com.epam.hw.hw5.service

import java.util.Map

import com.epam.hw.hw5.entity.CustomerEntity
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


    oneColumn.withColumn(CUSTOMER_ID, getMapValue(customers.value, CUSTOMER_ID)(col("data").getItem("clientId"))).show


    //        oneColumn.withColumn("clientName", customers.value.get(col("data").getItem(0))).show()
    //        oneColumn.withColumn("clientName", lit(customers.value.get(1).getName)).show()
    //        val name = customers.value.get(1).getName;
    //        oneColumn.withColumn("clientName", lit(customers.value.get(col("data").getItem(0)).getName)).show()

    //    val df = sqlContext.createDataFrame(Seq(
    //      (1, "a"), (2, "b"), (3, "c")
    //    )).toDF("id", "key")


    //    val lookupMap = Map("a" -> cusMap.get(1), "b" -> cusMap.get(2), "c" -> cusMap.get(3))
    //    val lookupMap = Map(1 -> cusMap.get(1), 2 -> cusMap.get(2), 3 -> cusMap.get(3))
    //    val lookupMap = Map(1 -> cusMap.get(1), 3 -> cusMap.get(3))
    val lookupMap = customers.value;

    //    df.withColumn("value", getMapValue(lookupMap)(col("id"))).show

  }

  def getMapValue(m: Map[Integer, CustomerEntity], fieldName: String) = udf {
    (key: Int) => {
      val value = m.get(key)
      getFieldValue(value, fieldName)
    }
  }

  def getFieldValue(value: CustomerEntity, fieldName: String): Integer = {
    if (value == null)
      null
    else {
      val classType = classOf[CustomerEntity]
      val fields = classType.getDeclaredFields;
      for (field <- fields) {
        if (field.getName == fieldName) {
          field.setAccessible(true)
          if (field.get(value).getClass == classOf[Integer]) {
            val fieldValue: Integer = field.get(value)
            return fieldValue;
          }
        }
      }
      null
    }
  }

}
