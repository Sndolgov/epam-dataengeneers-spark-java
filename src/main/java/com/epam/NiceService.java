package com.epam;

import org.apache.spark.api.java.JavaRDD;


/**
 * @author Evgeny Borisov
 */
public class NiceService {
    public long countSomething(JavaRDD<String> lines) {
        return lines.filter(s -> s.length() > 3).count();
    }
}
