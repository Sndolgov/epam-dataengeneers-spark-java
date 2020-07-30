package com.epam.udf_examples;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * @author Evgeny Borisov
 */
public interface ColumnAdder {
    Dataset<Row> addColumn(Dataset<Row> dataset);
}
