package com.epam.udf_examples;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Service;

/**
 * @author Evgeny Borisov
 */
@Service
public class ColumnAdderImpl implements ColumnAdder {
    @Override
    public Dataset<Row> addColumn(Dataset<Row> dataset) {
        return dataset.withColumn("A", functions.lit("A"));
    }
}
