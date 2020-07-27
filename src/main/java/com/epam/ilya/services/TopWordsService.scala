package com.epam.ilya.services

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SQLContext, functions}
import org.springframework.stereotype.Service

/**
 * @author Evgeny Borisov
 */
@Service
class TopWordsService(garbage: Broadcast[java.util.List[String]]) extends Serializable {


  def topXWithSqlStyle(sqlContext: SQLContext):Unit={
    val dataFrame: DataFrame = sqlContext.read.json("data/profiles.json")
    dataFrame.filter(col("age")>18)
      .withColumn("age*2",col("age")*2)



  }

  def topX(lines:RDD[String],x:Int):List[String]={




    lines.flatMap(_.split("[^a-zA-ZA-яЁё]+")) //splitting by non-chars
      .map(_.toLowerCase())
      .filter(!this.garbage.value.contains(_)) //filtering words from ignore-list
      .groupBy(identity)
      .mapValues(v => Integer.valueOf(v.count(_ => true)))
      .sortBy(_._2, ascending = false)
      .take(x).map(_._1).toList

  }


}
