package com.epam

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

/**
 * @author Evgeny Borisov
 */
object Spark {

  val sc: SparkContext = new SparkContext(master = "master[*]", appName = "spark-scala")


  def main(args: Array[String]): Unit = {
    val lines = sc.textFile("data/taxi_orders.txt")
    val tripRdd: RDD[Trip] = lines.map { line =>
      val strings: Array[String] = line.split(" ")
      strings match {
        case Array(id, city, km, _) => Trip(id.toInt, city, km.toInt)
        case Array(id, c1, c2, km, _) => Trip(id.toInt, c1 + " " + c2, km.toInt)
        case Array(id, c1, c2, c3, km, _) => Trip(id.toInt, c1 + c2 + c3, km.toInt)
      }
      //      Trip(id = strings(0).toInt, city = strings(1).toLowerCase, dist = strings(2).toInt)
    }.persist(StorageLevel.MEMORY_AND_DISK)


    println(s"count = ${tripRdd.count()}")

    val bostonRdd = tripRdd.filter(_.city == "boston").persist()
    println(s"long boston trips: ${bostonRdd.filter(_.dist > 10)}")

    println(s"total km to boston: ${bostonRdd.map(_.dist).reduce(_ + _)}")

    val byKmRdd = tripRdd.map(trip => (trip.id, trip.dist)).reduceByKey(_ + _)


    val driversRdd = sc.textFile("data/drivers.txt").map(_.split(", ")).map(arr => (arr(0).toInt, arr(1)))

    driversRdd.join(byKmRdd).sortBy(_._2._2, ascending = false)
      .take(3).foreach(println(_))


    println("***************************************")

    var shortCounter = sc.longAccumulator("short")
    var middleCounter = sc.longAccumulator("middle")
    var longCounter = sc.longAccumulator("long")

     tripRdd.foreach {
      case Trip(_, _, km) if km > 7 => longCounter.add(1)
      case Trip(_, _, km) if km < 7 => shortCounter.add(1)
      case Trip(_, _, km) if km == 7 => middleCounter.add(1)
    }

    println(shortCounter.value)
    println(middleCounter.value)
    println(longCounter.value)


  }


  case class Trip(id: Int, city: String, dist: Int)

}








