package com.subha.hyperlocal_market.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.subha.hyperlocal_market.dto.ProductsList;
import com.subha.hyperlocal_market.entity.Listing;
import com.subha.hyperlocal_market.repository.ListingRepo;


@Service
public class PublicService {

    private final ListingRepo listingRepo;

    PublicService(ListingRepo listingRepo) {
        this.listingRepo = listingRepo;
    }

    public List<ProductsList> getAllProducts() {

        List<Listing> listings = listingRepo.findAll();

        return listings.stream()
                .map(this::convertToProductList)
                .collect(Collectors.toList());
    }


    public List<ProductsList> getAllProductsByCategories(Long id) {

        List<Listing> listings = listingRepo.findByProductCategoryId(id);

        return listings.stream().map(this::convertToProductList)
                .collect(Collectors.toList());
    }

    private ProductsList convertToProductList(Listing listing) {

        ProductsList product = new ProductsList();

        product.setId(listing.getId());
        product.setTitle(listing.getProduct().getTitle());
        product.setDescription(listing.getProduct().getDescription());
        product.setPrice(listing.getPrice());
        product.setImageUrl(null);
        product.setLocation(null);

        return product;
    }

    public List<ProductsList> searchProduct(String keyword) {
        return listingRepo.searchProduct(keyword)
            .stream()
            .map(this::convertToProductList)
            .collect(Collectors.toList());
        
    }

}
