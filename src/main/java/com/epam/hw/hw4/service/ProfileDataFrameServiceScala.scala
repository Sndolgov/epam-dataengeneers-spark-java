package com.epam.hw.hw4.service

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.{ContextRefreshedEvent, EventListener}
import org.springframework.stereotype.Service

@Service
class ProfileDataFrameServiceScala(sqlContext: SQLContext) extends ProfileDataFrameService {

  private var filePath: String = _

  private var profiles: DataFrame = _
  private var profilesWithSalary: DataFrame = _

  @EventListener def setDataSet(event: ContextRefreshedEvent): Unit = {
    profiles = sqlContext.read.json(filePath)
  }

  @Value("${profile.file.path:data/profiles.json}")
  private def setFilePath(value: String): Unit =
    filePath = value

  override def printContent(): Unit = profiles.show()

  override def printSchema(): Unit = profiles.printSchema()

  override def printColumnTypes(): Unit = profiles.dtypes.foreach(a => println("Column name: " + a._1 + " - type: " + a._2))

  override def addColumnSalaryAndPrint(): Unit = {
    getProfilesWithSalary
    profilesWithSalary.show();
  }

  private def getProfilesWithSalary(): Unit = {
    profilesWithSalary = profiles.withColumn("salary", col("age").*(size(col("keywords"))).*(10))
  }

  override def lowestPaidWithMostPopularTechnology(): Unit = {
    if (profilesWithSalary == null)
      getProfilesWithSalary
    val mostPopularTechnology = profilesWithSalary
      .withColumn("technology", explode(col("keywords")))
      .groupBy("technology")
      .count
      .sort(desc("count"))
      .first

    profilesWithSalary.filter(col("salary")<(1200))
      .filter(array_contains(col("keywords"), mostPopularTechnology.get(0)))
      .show()
  }
}
