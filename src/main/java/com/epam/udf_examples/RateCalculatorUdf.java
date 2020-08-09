package com.epam.udf_examples;

import org.apache.spark.sql.api.java.UDF1;
import scala.collection.Seq;

/**
 * @author Evgeny Borisov
 */

public class RateCalculatorUdf implements UDF1<Seq<String>,Integer> {


    @Override
    public Integer call(Seq<String> keywords) throws Exception {

        return 1;
    }
}
