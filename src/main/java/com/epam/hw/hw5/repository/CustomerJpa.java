package com.epam.hw.hw5.repository;

import com.epam.hw.hw5.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpa extends JpaRepository<CustomerEntity, Integer>
{
}
