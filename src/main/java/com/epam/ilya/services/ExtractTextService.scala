package com.epam.ilya.services

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.springframework.stereotype.Service

/**
 * @author Evgeny Borisov
 */
@Service
class ExtractTextService(val sc:SparkContext) {

  def readLinesFromFile(path:String):RDD[String]={
    sc.textFile(path)
  }

}
