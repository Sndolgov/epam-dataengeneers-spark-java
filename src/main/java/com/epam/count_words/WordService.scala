package com.epam.count_words

import com.epam.andrey.Point
import org.apache.spark.rdd.RDD

/**
 * @author Evgeny Borisov
 */
class WordService {

  def topX(wordsRdd:RDD[String],x:Int):List[String]={
    wordsRdd.map(_.toLowerCase)
      .map(word=>(word,1))
      .reduceByKey(_+_)
      .map(_.swap)
      .sortByKey(ascending = false)
      .map(_._2)
      .take(x)
      .toList

  }

}
