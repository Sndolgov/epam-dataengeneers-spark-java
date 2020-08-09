package com.epam.udf_examples
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Dataset, Row, functions}
import org.springframework.stereotype.Service

/**
 * @author Evgeny Borisov
 */
@Service
class GenaAdder extends ColumnAdder with Serializable {
  override def addColumn(dataset: Dataset[Row]): Dataset[Row] = {
    val genaFunc:String=>Int = nameSize
    val udfGenaFunc = functions.udf(genaFunc)

    dataset.withColumn("name size",udfGenaFunc(col("name")))
  }

  def nameSize(name:String): Int =name.length



}
