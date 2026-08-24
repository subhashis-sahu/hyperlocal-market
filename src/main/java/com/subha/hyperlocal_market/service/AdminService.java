package com.subha.hyperlocal_market.service;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.subha.hyperlocal_market.dto.AttributeOptionDto;
import com.subha.hyperlocal_market.dto.CategoryAttributeDto;
import com.subha.hyperlocal_market.dto.CategoryDto;
import com.subha.hyperlocal_market.entity.AttributeOption;
import com.subha.hyperlocal_market.entity.Category;
import com.subha.hyperlocal_market.entity.CategoryAttribute;
import com.subha.hyperlocal_market.entity.User;
import com.subha.hyperlocal_market.repository.AttributeOptionRepo;
import com.subha.hyperlocal_market.repository.CategoryAttributeRepo;
import com.subha.hyperlocal_market.repository.CategoryRepo;
import com.subha.hyperlocal_market.repository.UsersRepo;

@Service
public class AdminService {

    private final CategoryRepo categoryRepo;
    private final UsersRepo usersRepo;
    private final CategoryAttributeRepo categoryAttributeRepo;
    private final AttributeOptionRepo attributeOptionRepo;

    AdminService(
            CategoryRepo categoryRepo,
            UsersRepo usersRepo,
            CategoryAttributeRepo categoryAttributeRepo,
            AttributeOptionRepo attributeOptionRepo) {

        this.categoryRepo = categoryRepo;
        this.usersRepo = usersRepo;
        this.categoryAttributeRepo = categoryAttributeRepo;
        this.attributeOptionRepo = attributeOptionRepo;
    }

    public ResponseEntity<?> addCategoryAttribute(Long user, CategoryAttributeDto dto) {

        User existUser = usersRepo.findById(user).orElse(null);

        if (existUser == null) {
            return ResponseEntity
                    .badRequest()
                    .body("User not found");
        }

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElse(null);

        if (category == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Category not found");
        }

        if (categoryAttributeRepo
                .existsByCategoryIdAndSlug(
                        dto.getCategoryId(),
                        dto.getSlug())) {

            return ResponseEntity
                    .badRequest()
                    .body("Attribute already exists for this category");
        }

        CategoryAttribute attribute = CategoryAttribute.builder()
                .category(category)
                .name(dto.getName())
                .slug(dto.getSlug())
                .dataType(dto.getDataType())
                .unit(dto.getUnit())
                .required(dto.isRequired())
                .filterable(dto.isFilterable())
                .searchable(dto.isSearchable())
                .comparable(dto.isComparable())
                .displayOrder(dto.getDisplayOrder())
                .active(true)
                .build();

        categoryAttributeRepo.save(attribute);

        return ResponseEntity.ok("Category attribute added successfully");
    }

    public ResponseEntity<?> addCategory(Long user, CategoryDto category) {

        User existuser = usersRepo.findById(user).orElse(null);

        if (existuser != null) {
            Category existingCategory = categoryRepo.findByName(category.getName());

            if (existingCategory != null) {
                return ResponseEntity
                        .badRequest()
                        .body("Category already exists");
            }

            Category newCategory = new Category();
            newCategory.setName(category.getName());

            categoryRepo.save(newCategory);

        }

        return ResponseEntity.ok("Category added successfully");
    }

    public ResponseEntity<?> addAttributeOption(
            Long user,
            AttributeOptionDto dto) {

        User existUser = usersRepo.findById(user).orElse(null);

        if (existUser == null) {
            return ResponseEntity
                    .badRequest()
                    .body("User not found");
        }

        CategoryAttribute attribute = categoryAttributeRepo.findById(dto.getAttributeId())
                .orElse(null);

        if (attribute == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Category attribute not found");
        }

        if (attribute.getDataType() != com.subha.hyperlocal_market.enums.AttributeDataType.SELECT
                &&
                attribute.getDataType() != com.subha.hyperlocal_market.enums.AttributeDataType.MULTI_SELECT) {

            return ResponseEntity
                    .badRequest()
                    .body("Options are allowed only for SELECT or MULTI_SELECT attributes");
        }

        if (attributeOptionRepo
                .existsByAttributeIdAndValue(
                        dto.getAttributeId(),
                        dto.getValue())) {

            return ResponseEntity
                    .badRequest()
                    .body("Option already exists");
        }

        AttributeOption option = AttributeOption.builder()
                .attribute(attribute)
                .value(dto.getValue())
                .displayName(dto.getDisplayName())
                .displayOrder(dto.getDisplayOrder())
                .build();

        attributeOptionRepo.save(option);

        return ResponseEntity.ok("Attribute option added successfully");
    }

}
