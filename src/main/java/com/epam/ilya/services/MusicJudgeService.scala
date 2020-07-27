package com.epam.ilya.services

import org.apache.spark.rdd.RDD
import org.springframework.stereotype.Service

/**
 * @author Evgeny Borisov
 */
@Service
class MusicJudgeService(extractDataService:ExtractTextService,wordsService:TopWordsService) {

  def printTopWordsOfBand(bandName:String,x:Int): Unit ={
    val linesRdd: RDD[String] = extractDataService.readLinesFromFile(s"data/$bandName/*")
    val list = wordsService.topX(linesRdd, x)
    println(list)
  }

}
