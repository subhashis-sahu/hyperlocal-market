package com.subha.hyperlocal_market.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsList {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String location;
    
}
