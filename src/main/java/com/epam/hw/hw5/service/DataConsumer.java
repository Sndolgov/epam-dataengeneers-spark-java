package com.epam.hw.hw5.service;

public interface DataConsumer
{
    String CUSTOMER_ID = "customerId";
    String PRODUCT_ID = "productId";
    String NAME = "name";
    String CUSTOMER_NAME = "customerName";
    String PRODUCT_NAME = "productName";
    String GENDER = "gender";
    String AGE = "age";
    String BIRTH_DATE = "birthDate";
    String PRICE = "price";
    String KEYWORD = "keyword";

    void readData();

}
