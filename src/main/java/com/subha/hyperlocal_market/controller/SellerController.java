package com.subha.hyperlocal_market.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subha.hyperlocal_market.dto.CategoryAttributeDto;
import com.subha.hyperlocal_market.dto.CreateListingRequest;
import com.subha.hyperlocal_market.service.CategoryAttributeService;
import com.subha.hyperlocal_market.service.SellerService;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    //     POST   /api/seller/listings
    // PUT    /api/seller/listings/{id}
    // DELETE /api/seller/listings/{id}
    // GET    /api/seller/listings
    // GET    /api/seller/listings/{id}

    private final SellerService sellerService;
    private final CategoryAttributeService categoryAttributeService;


    SellerController(SellerService sellerService,CategoryAttributeService categoryAttributeService){
        this.sellerService=sellerService;
        this.categoryAttributeService=categoryAttributeService;
    }
    


    @PostMapping("/listings")
    public ResponseEntity<?> addProduct(Authentication authentication,@RequestBody CreateListingRequest request){
        Long user=Long.parseLong(authentication.getName());
        return sellerService.createListing(user,request);


    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(){
        return sellerService.getCategories();
    }

    

    @GetMapping("/{categoryId}/attributes")
    public List<CategoryAttributeDto> getCategoryAttributes(
            @PathVariable Long categoryId) {

        return categoryAttributeService
                .getAttributesByCategory(categoryId);
    }



    
}
