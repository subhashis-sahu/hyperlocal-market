package com.subha.hyperlocal_market.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeOptionDto {

    private Long attributeId;

    private String value;

    private String displayName;

    private Integer displayOrder;
}