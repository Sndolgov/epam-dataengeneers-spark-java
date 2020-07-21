package com.epam.hw.hw2.taxis

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object SparkTaxi {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(master = "local", appName = "spark-taxi")
    val rdd = sc.textFile("/Users/sergeydolgov/Downloads/taxi_orders.txt")
    val rddDrivers = sc.textFile("/Users/sergeydolgov/Downloads/drivers.txt")
    println("countLines: " + countLines(rdd))
    println("countBostonOrdersMore10: " + countBostonOrdersMore10(rdd))
    println("countBostonOrdersAmountDistance: " + countBostonOrdersAmountDistance(rdd))
    println("countDriversWithMostDistance: " + countDriversWithMostDistance(rdd, rddDrivers, 3))
  }

  def countLines(rdd: RDD[String]): Long = {
    rdd.count()
  }

  def countBostonOrdersMore10(rdd: RDD[String]): Long = {
    /*rdd.map(line => {
      line.split(" ") match {
        case Array(id, city, distance, date) => (id.toInt, city.toLowerCase, distance.toInt, date)
      }
    })*/
    rdd.map(_.split(" "))
      .map(l => (l(0).toInt, l(1).toLowerCase(), l(2).toInt))
      .filter(_._2 == "boston")
      .filter(_._3 > 10)
      .count()
  }

  def countBostonOrdersAmountDistance(rdd: RDD[String]): Double = {
    rdd.map(_.split(" "))
      .map(l => (l(0).toInt, l(1).toLowerCase(), l(2).toInt))
      .filter(_._2 == "boston")
      .map(_._3)
      .sum()
  }

  def countDriversWithMostDistance(rddCities: RDD[String], rddDrivers: RDD[String], numberOfDrivers: Int): Any = {
    val drivers = rddDrivers.map(_.split(", "))
      .map(l => (l(0).toInt, (l(1), l(2), l(3))))

    rddCities.map(_.split(" "))
      .map(l => (l(0).toInt, l(2).toInt))
      .reduceByKey(_ + _)
      .join(drivers)
      .sortBy(_._2, ascending = false)
      .take(numberOfDrivers)
      .foreach(x => println(x))
  }
}
