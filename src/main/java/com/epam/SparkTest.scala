package com.epam

import com.epam.count_words.WordService
import com.github.javafaker.Faker
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author Evgeny Borisov
 */
object SparkTest {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(master = "local", appName = "spark-scala")

    val faker = new Faker()

    var list:List[String]=Nil

    (1 to 1000000).foreach(_=>list=faker.name().username()::list)


    var rdd = sc.parallelize(list)

    rdd=rdd.repartition(10)

    new WordService().topX(rdd,5).foreach(println(_))
    Thread.sleep(10000000)
  }
}
