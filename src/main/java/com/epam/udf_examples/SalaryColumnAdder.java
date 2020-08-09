package com.epam.udf_examples;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.size;

/**
 * @author Evgeny Borisov
 */
@Service
public class SalaryColumnAdder implements ColumnAdder {
    @Override
    public Dataset<Row> addColumn(Dataset<Row> dataset) {
        return dataset.withColumn("salary", col("age").multiply(size(col("keywords"))));

    }
}
