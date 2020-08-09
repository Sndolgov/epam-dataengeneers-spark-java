package com.epam.udf_examples;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

/**
 * @author Evgeny Borisov
e */
@Service
public class LinkedInService {

    @Autowired
    private List<ColumnAdder> columnAdders;


    public Dataset<Row> enrich(Dataset<Row> dataset) {
        for (ColumnAdder columnAdder : columnAdders) {
            dataset = columnAdder.addColumn(dataset);
        }

        return dataset.withColumn("dog age", callUDF(DogAgeConvector.class.getName(), col("age")));
    }




}









