package com.subha.hyperlocal_market.dto;

import java.math.BigDecimal;
import java.util.List;

import com.subha.hyperlocal_market.enums.ListingType;
import com.subha.hyperlocal_market.enums.ProductCondition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateListingRequest {

    private Long categoryId;

    private String title;

    private String description;

    private List<ProductAttributeRequest> attributes;

    private List<String> imageUrls;

    private BigDecimal price;

    private ProductCondition condition;

    private ListingType listingType;
}