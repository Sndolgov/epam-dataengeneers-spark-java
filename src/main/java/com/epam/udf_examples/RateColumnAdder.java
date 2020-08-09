package com.epam.udf_examples;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

/**
 * @author Evgeny Borisov
 */
@Service
public class RateColumnAdder implements ColumnAdder {
    @Override
    public Dataset<Row> addColumn(Dataset<Row> dataset) {
        return dataset.withColumn("keywords score",
                callUDF(RateScalaUdf.class.getName(),col("keywords"))
        );
    }
}
