package com.subha.hyperlocal_market.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductAttributeRequest {

    private Long attributeId;

    private String value;

    private Long optionId;

    // Used for MULTI_SELECT
    private List<Long> optionIds;
}