package com.subha.hyperlocal_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.subha.hyperlocal_market.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

    Category findByName(String name);
    
}
