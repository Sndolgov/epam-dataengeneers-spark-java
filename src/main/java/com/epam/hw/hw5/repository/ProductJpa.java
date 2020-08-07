package com.epam.hw.hw5.repository;

import com.epam.hw.hw5.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpa extends JpaRepository<ProductEntity, Integer>
{
}
