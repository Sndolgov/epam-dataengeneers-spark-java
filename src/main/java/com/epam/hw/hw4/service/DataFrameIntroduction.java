package com.epam.hw.hw4.service;


import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import java.io.File;
import java.util.Arrays;

public class DataFrameIntroduction
{
    private Dataset<Row> dataFrame;


    public DataFrameIntroduction(String filePath)
    {
        SQLContext sqlContext = new SQLContext(new SparkContext(new SparkConf().setAppName("dataFrame").setMaster("local[*]")));
        dataFrame = sqlContext.read().json(filePath);
    }

    public void printContent(){
        dataFrame.show();
    }

    public void printSchema(){
        dataFrame.printSchema();
    }

    public void printColumnTypes(){
        Arrays.asList(dataFrame.dtypes()).forEach(t-> System.out.println("Column name: " + t._1 + " - type: " + t._2));
    }

    public static void main(String[] args)
    {
        File file = new File("data/profiles.json");
        System.out.println(file.exists());
        DataFrameIntroduction dataFrameIntroduction = new DataFrameIntroduction("data/profiles.json");
        dataFrameIntroduction.printContent();
        dataFrameIntroduction.printSchema();
        dataFrameIntroduction.printColumnTypes();
    }

}
