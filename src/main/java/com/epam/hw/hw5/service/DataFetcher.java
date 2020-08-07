package com.epam.hw.hw5.service;

import com.epam.hw.hw5.entity.CustomerEntity;
import com.epam.hw.hw5.entity.ProductEntity;
import com.epam.hw.hw5.repository.CustomerJpa;
import com.epam.hw.hw5.repository.ProductJpa;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DataFetcher
{
    @Autowired
    private CustomerJpa customerJpa;

    @Autowired
    private ProductJpa productJpa;

    @Getter
    List<CustomerEntity> customers;
    @Getter
    List<ProductEntity> products;

    @PostConstruct
    private void fetchData(){
        customers = customerJpa.findAll();
        products = productJpa.findAll();
    }

}
