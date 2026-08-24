package com.subha.hyperlocal_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.subha.hyperlocal_market.entity.ProductImage;

@Repository
public interface ProductImageRepo
        extends JpaRepository<ProductImage, Long> {
}