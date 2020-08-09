package com.epam.udf_examples;

import org.apache.spark.sql.api.java.UDF1;

/**
 * @author Evgeny Borisov
 */
@RegisterUdf
public class DogAgeConvector implements UDF1<Long, Integer> {
    @Override
    public Integer call(Long age) throws Exception {
        return Math.toIntExact(age / 7);
    }
}
