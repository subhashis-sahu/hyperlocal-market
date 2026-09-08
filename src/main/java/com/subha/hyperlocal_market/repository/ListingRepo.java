package com.subha.hyperlocal_market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.subha.hyperlocal_market.entity.Listing;

@Repository
public interface ListingRepo extends JpaRepository<Listing,Long> {

    List<Listing> findByProductCategoryId(Long categoryId);

    

    @Query("""
            SELECT l
            FROM Listing l
            JOIN l.product p
            WHERE l.status = com.subha.hyperlocal_market.enums.ListingStatus.ACTIVE
            AND(
                LOWER(p.title) LIKE LOWER(CONCAT('%',:keyword, '%'))
                OR LOWER(p.description) LIKE LOWER(CONCAT('%',:keyword, '%'))
            )
            """)
    List<Listing> searchProduct(@Param("keyword") String keyword);
    
}
