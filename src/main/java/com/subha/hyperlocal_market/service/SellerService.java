package com.subha.hyperlocal_market.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.subha.hyperlocal_market.dto.CreateListingRequest;
import com.subha.hyperlocal_market.dto.ProductAttributeRequest;
import com.subha.hyperlocal_market.entity.AttributeOption;
import com.subha.hyperlocal_market.entity.Category;
import com.subha.hyperlocal_market.entity.CategoryAttribute;
import com.subha.hyperlocal_market.entity.Listing;
import com.subha.hyperlocal_market.entity.Product;
import com.subha.hyperlocal_market.entity.ProductAttributeSelectedOption;
import com.subha.hyperlocal_market.entity.ProductAttributeValue;
import com.subha.hyperlocal_market.entity.ProductImage;
import com.subha.hyperlocal_market.entity.User;
import com.subha.hyperlocal_market.enums.AttributeDataType;
import com.subha.hyperlocal_market.repository.AttributeOptionRepo;
import com.subha.hyperlocal_market.repository.CategoryAttributeRepo;
import com.subha.hyperlocal_market.repository.CategoryRepo;
import com.subha.hyperlocal_market.repository.ListingRepo;
import com.subha.hyperlocal_market.repository.ProductAttributeSelectedOptionRepo;
import com.subha.hyperlocal_market.repository.ProductAttributeValueRepo;
import com.subha.hyperlocal_market.repository.ProductImageRepo;
import com.subha.hyperlocal_market.repository.ProductRepo;
import com.subha.hyperlocal_market.repository.UsersRepo;

@Service
public class SellerService {

    private final ListingRepo listingRepo;
    private final UsersRepo usersRepo;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final CategoryAttributeRepo categoryAttributeRepo;
    private final AttributeOptionRepo attributeOptionRepo;
    private final ProductAttributeValueRepo productAttributeValueRepo;
    private final ProductAttributeSelectedOptionRepo selectedOptionRepo;
    private final ProductImageRepo productImageRepo;

    public SellerService(
            ListingRepo listingRepo,
            UsersRepo usersRepo,
            CategoryRepo categoryRepo,
            ProductRepo productRepo,
            CategoryAttributeRepo categoryAttributeRepo,
            AttributeOptionRepo attributeOptionRepo,
            ProductAttributeValueRepo productAttributeValueRepo,
            ProductAttributeSelectedOptionRepo selectedOptionRepo,
            ProductImageRepo productImageRepo) {

        this.listingRepo = listingRepo;
        this.usersRepo = usersRepo;
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.categoryAttributeRepo = categoryAttributeRepo;
        this.attributeOptionRepo = attributeOptionRepo;
        this.productAttributeValueRepo = productAttributeValueRepo;
        this.selectedOptionRepo = selectedOptionRepo;
        this.productImageRepo = productImageRepo;
    }

    @Transactional
    public ResponseEntity<?> createListing(
            Long userId,
            CreateListingRequest request) {

        User seller = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // create Product
        Product product = new Product();

        product.setCategory(category);
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());

        product = productRepo.save(product);

        // Save Dynamic Attributes

        if (request.getAttributes() != null) {

            for (ProductAttributeRequest attributeRequest : request.getAttributes()) {

                CategoryAttribute attribute = categoryAttributeRepo.findById(attributeRequest.getAttributeId())
                        .orElseThrow(() -> new RuntimeException(
                                "Attribute not found"));

                // Make sure attribute belongs to selected category
                if (!attribute.getCategory().getId()
                        .equals(category.getId())) {

                    throw new RuntimeException(
                            "Attribute does not belong to selected category");
                }

                ProductAttributeValue attributeValue = new ProductAttributeValue();

                attributeValue.setProduct(product);
                attributeValue.setAttribute(attribute);

                AttributeDataType dataType = attribute.getDataType();

                
                // TEXT
                

                if (dataType == AttributeDataType.TEXT) {

                    attributeValue.setTextValue(
                            attributeRequest.getValue());
                }

                // ----------------------------------------
                // NUMBER
                // ----------------------------------------

                else if (dataType == AttributeDataType.NUMBER) {

                    attributeValue.setNumberValue(
                            new BigDecimal(
                                    attributeRequest.getValue()));
                }

                // ----------------------------------------
                // BOOLEAN
                // ----------------------------------------

                else if (dataType == AttributeDataType.BOOLEAN) {

                    attributeValue.setBooleanValue(
                            Boolean.parseBoolean(
                                    attributeRequest.getValue()));
                }

                // ----------------------------------------
                // DATE
                // ----------------------------------------

                else if (dataType == AttributeDataType.DATE) {

                    attributeValue.setDateValue(
                            LocalDate.parse(
                                    attributeRequest.getValue()));
                }

                // ----------------------------------------
                // SELECT
                // ----------------------------------------

                else if (dataType == AttributeDataType.SELECT) {

                    if (attributeRequest.getOptionId() == null) {

                        throw new RuntimeException(
                                "Option is required for SELECT attribute");
                    }

                    AttributeOption option = attributeOptionRepo
                            .findById(
                                    attributeRequest.getOptionId())
                            .orElseThrow(() -> new RuntimeException(
                                    "Option not found"));

                    // Verify option belongs to this attribute
                    if (!option.getAttribute().getId()
                            .equals(attribute.getId())) {

                        throw new RuntimeException(
                                "Invalid option for attribute");
                    }

                    attributeValue.setSelectedOption(option);
                }

                // ----------------------------------------
                // MULTI_SELECT
                // ----------------------------------------

                else if (dataType == AttributeDataType.MULTI_SELECT) {

                    if (attributeRequest.getOptionIds() == null
                            || attributeRequest.getOptionIds().isEmpty()) {

                        throw new RuntimeException(
                                "Options are required for MULTI_SELECT");
                    }
                }

                // Save attribute value first
                attributeValue = productAttributeValueRepo.save(attributeValue);

                // ----------------------------------------
                // Save MULTI_SELECT options
                // ----------------------------------------

                if (dataType == AttributeDataType.MULTI_SELECT) {

                    for (Long optionId : attributeRequest.getOptionIds()) {

                        AttributeOption option = attributeOptionRepo
                                .findById(optionId)
                                .orElseThrow(() -> new RuntimeException(
                                        "Option not found"));

                        if (!option.getAttribute().getId()
                                .equals(attribute.getId())) {

                            throw new RuntimeException(
                                    "Invalid option for attribute");
                        }

                        ProductAttributeSelectedOption selectedOption = new ProductAttributeSelectedOption();

                        selectedOption.setProductAttributeValue(
                                attributeValue);

                        selectedOption.setOption(option);

                        selectedOptionRepo.save(selectedOption);
                    }
                }
            }
        }

        // ------------------------------------------------
        // 5. Save Product Images
        // ------------------------------------------------

        if (request.getImageUrls() != null) {

            int order = 0;

            for (String imageUrl : request.getImageUrls()) {

                ProductImage image = new ProductImage();

                image.setProduct(product);
                image.setImageUrl(imageUrl);
                image.setDisplayOrder(order);
                image.setPrimaryImage(order == 0);

                productImageRepo.save(image);

                order++;
            }
        }

        // ------------------------------------------------
        // 6. Create Listing
        // ------------------------------------------------

        Listing listing = new Listing();

        listing.setProduct(product);
        listing.setSeller(seller);
        listing.setPrice(request.getPrice());
        listing.setCondition(request.getCondition());
        listing.setListingType(request.getListingType());

        // status automatically becomes ACTIVE
        // because of @PrePersist

        listingRepo.save(listing);

        // ------------------------------------------------
        // 7. Response
        // ------------------------------------------------

        return ResponseEntity.ok(
                "Listing created successfully");
    }

    public ResponseEntity<?> getCategories() {
        List<Category> categories=categoryRepo.findAll();
        return ResponseEntity.ok(categories);
    }
}