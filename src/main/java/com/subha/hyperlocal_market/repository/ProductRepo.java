package com.subha.hyperlocal_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subha.hyperlocal_market.entity.Product;

public interface ProductRepo extends JpaRepository<Product,Long>{
    
}
