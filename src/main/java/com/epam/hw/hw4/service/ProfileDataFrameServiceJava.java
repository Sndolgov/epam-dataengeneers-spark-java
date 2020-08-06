package com.epam.hw.hw4.service;

import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

import static org.apache.spark.sql.functions.*;

@Service
public class ProfileDataFrameServiceJava implements ProfileDataFrameService
{


    @Autowired
    private SQLContext sqlContext;

    @Autowired
    private SparkContext sparkContext;

//    @Autowired
//    private StreamingContext streamingContext;

    @Value("${profile.file.path:data/profiles.json}")
    private String filePath;

    private Dataset<Row> profiles;
    private Dataset<Row> profilesWithSalary;

    @PostConstruct
    public void setDataSet()
    {
        profiles = sqlContext.read().json(filePath);
    }


    @Override
    public void printContent()
    {
        profiles.show();
    }

    @Override
    public void printSchema()
    {
        profiles.printSchema();
    }

    @Override
    public void printColumnTypes()
    {
        Arrays.asList(profiles.dtypes()).forEach(t -> System.out.println("Column name: " + t._1 + " - type: " + t._2));
    }

    @Override
    public void addColumnSalaryAndPrint()
    {
        profilesWithSalary = getProfilesWithSalary();
        profilesWithSalary.show();
    }

    private Dataset<Row> getProfilesWithSalary(){
        return profiles.withColumn("salary", col("age").multiply(size(col("keywords"))).multiply(10));
    }

    @Override
    public void lowestPaidWithMostPopularTechnology()
    {
        if (profilesWithSalary == null)
            profilesWithSalary = getProfilesWithSalary();
        Row mostPopularTechnology = profilesWithSalary
                .withColumn("technology", explode(col("keywords")))
                .groupBy("technology")
                .count()
                .sort(desc("count"))
                .first();

        profilesWithSalary
                .filter(col("salary").lt(1200))
                .filter(array_contains(col("keywords"), mostPopularTechnology.get(0)))
                .show();
    }
}
