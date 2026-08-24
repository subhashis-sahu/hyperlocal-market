package com.subha.hyperlocal_market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.subha.hyperlocal_market.entity.CategoryAttribute;

@Repository
public interface CategoryAttributeRepo
        extends JpaRepository<CategoryAttribute, Long> {

        boolean existsByCategoryIdAndSlug(Long categoryId, String slug);
        List<CategoryAttribute> findByCategoryIdAndActiveTrueOrderByDisplayOrderAsc(Long categoryId);

}