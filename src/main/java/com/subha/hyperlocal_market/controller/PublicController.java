package com.subha.hyperlocal_market.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subha.hyperlocal_market.dto.ProductsList;
import com.subha.hyperlocal_market.service.PublicService;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final PublicService pService;

    PublicController(PublicService pService){
        this.pService=pService;
    }



    @GetMapping("/products")
    public List<ProductsList> getAllProducts(){
        return pService.getAllProducts();


    }

    @GetMapping("/products/byCategories/{id}")
    public List<ProductsList> getProductsByCategory(@PathVariable Long id){
        return pService.getAllProductsByCategories(id);
    }
    
}
