package com.epam.hw.hw5.service;

import com.epam.hw.hw5.entity.CustomerEntity;
import com.epam.hw.hw5.entity.ProductEntity;
import com.epam.hw.hw5.repository.CustomerJpa;
import com.epam.hw.hw5.repository.ProductJpa;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataFetcher
{
    @Autowired
    private CustomerJpa customerJpa;

    @Autowired
    private ProductJpa productJpa;

    @Getter
    Map<Integer, CustomerEntity> customers;
    @Getter
    Map<Integer, ProductEntity> products;

    @PostConstruct
    void fetchData(){
        customers =customerJpa.findAll().stream().collect(Collectors.toMap(CustomerEntity::getCustomerId, c->c));
        products =productJpa.findAll().stream().peek(p-> p.setKeyword(p.getProductGroup().getKeyword())).collect(Collectors.toMap(ProductEntity::getProductId, c->c));
    }

}
