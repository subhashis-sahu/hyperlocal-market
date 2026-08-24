package com.subha.hyperlocal_market.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.subha.hyperlocal_market.dto.AttributeOptionDto;
import com.subha.hyperlocal_market.dto.CategoryAttributeDto;
import com.subha.hyperlocal_market.entity.AttributeOption;
import com.subha.hyperlocal_market.entity.CategoryAttribute;
import com.subha.hyperlocal_market.repository.AttributeOptionRepo;
import com.subha.hyperlocal_market.repository.CategoryAttributeRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryAttributeService {

    private final CategoryAttributeRepo categoryAttributeRepo;
    private final AttributeOptionRepo attributeOptionRepo;

    public List<CategoryAttributeDto> getAttributesByCategory(
            Long categoryId
    ) {

        List<CategoryAttribute> attributes =
                categoryAttributeRepo
                        .findByCategoryIdAndActiveTrueOrderByDisplayOrderAsc(
                                categoryId
                        );

        List<CategoryAttributeDto> responses =
                new ArrayList<>();

        for (CategoryAttribute attribute : attributes) {

            CategoryAttributeDto response =
                    new CategoryAttributeDto();

            response.setCategoryId(attribute.getId());
            response.setName(attribute.getName());
            response.setSlug(attribute.getSlug());
            response.setDataType(attribute.getDataType());
            response.setUnit(attribute.getUnit());
            response.setRequired(attribute.isRequired());
            response.setActive(attribute.isActive());
            response.setDisplayOrder(attribute.getDisplayOrder());

            List<AttributeOption> options =
                    attributeOptionRepo
                            .findByAttributeIdOrderByDisplayOrderAsc(
                                    attribute.getId()
                            );

            List<AttributeOptionDto> optionResponses =
                    new ArrayList<>();

            for (AttributeOption option : options) {

                AttributeOptionDto optionResponse =
                        new AttributeOptionDto();

                optionResponse.setAttributeId(option.getId());
                optionResponse.setValue(option.getValue());
                optionResponse.setDisplayName(
                        option.getDisplayName()
                );
                optionResponse.setDisplayOrder(
                        option.getDisplayOrder()
                );

                optionResponses.add(optionResponse);
            }

            response.setOptions(optionResponses);

            responses.add(response);
        }

        return responses;
    }
}