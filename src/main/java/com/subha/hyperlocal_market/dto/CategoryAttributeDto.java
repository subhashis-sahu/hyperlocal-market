package com.subha.hyperlocal_market.dto;

import java.util.List;

import com.subha.hyperlocal_market.enums.AttributeDataType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAttributeDto {

    private Long categoryId;

    private String name;

    private String slug;

    private AttributeDataType dataType;

    private String unit;

    private boolean required;

    private boolean filterable;

    private boolean searchable;

    private boolean comparable;

    private boolean isActive;

    private Integer displayOrder;
    private List<AttributeOptionDto> options;
}