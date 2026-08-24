package com.subha.hyperlocal_market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.subha.hyperlocal_market.entity.Listing;
@Repository
public interface ListingRepo extends JpaRepository<Listing,Long> {

    List<Listing> findByProductCategoryId(Long categoryId);
    
}
