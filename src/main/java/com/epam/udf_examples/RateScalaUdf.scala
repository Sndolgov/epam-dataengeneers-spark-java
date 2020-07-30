package com.epam.udf_examples

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.api.java.UDF1
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Evgeny Borisov
 */
class RateScalaUdf extends UDF1[Seq[String], Int] {
  @Autowired private val techRateMap: Broadcast[java.util.Map[String, Integer]] = null


  override def call(keywords: Seq[String]): Int = {
    keywords.map(keyword=>techRateMap.value.getOrDefault(keyword,1)).reduce(_+_)
  }
}









