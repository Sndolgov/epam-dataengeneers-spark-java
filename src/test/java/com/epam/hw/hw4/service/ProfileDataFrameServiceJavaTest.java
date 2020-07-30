package com.epam.hw.hw4.service;

import com.epam.hw.hw4.MainConfHW4;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.epam.ilya.conf.SparkConfig.DEV;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainConfHW4.class)
@ActiveProfiles(DEV)
public class ProfileDataFrameServiceJavaTest
{

    @Autowired
    private ProfileDataFrameServiceJava serviceJava;
    @Autowired
    private ProfileDataFrameServiceScala serviceScala;

    @Test
    public void mainJava()
    {
        serviceJava.printContent();
        serviceJava.printSchema();
        serviceJava.printColumnTypes();
        serviceJava.addColumnSalaryAndPrint();
        serviceJava.lowestPaidWithMostPopularTechnology();

    }
    @Test
    public void mainScala()
    {
        serviceScala.printContent();
        serviceScala.printSchema();
        serviceScala.printColumnTypes();
        serviceScala.addColumnSalaryAndPrint();
        serviceScala.lowestPaidWithMostPopularTechnology();

    }
}