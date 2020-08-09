package com.epam.hw.hw5.service;

public interface DataConsumer
{
    String CUSTOMER_ID = "customerId";
    String NAME = "name";
    String NAME_CUSTOMER = "nameCustomer";
    String NAME_PRODUCT = "nameProduct";
    String GENDER = "gender";
    String AGE = "age";
    String BIRTH_DATE = "birthDate";
    String PRICE = "price";
    String KEYWORD = "keyword";

    void readData();

}
