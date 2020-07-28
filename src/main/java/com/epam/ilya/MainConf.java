package com.epam.ilya;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.epam.ilya.conf.SparkConfig.DEV;
import static java.util.Arrays.asList;

/**
 * @author Evgeny Borisov
 */
@Configuration
@ComponentScan
public class MainConf {

}
