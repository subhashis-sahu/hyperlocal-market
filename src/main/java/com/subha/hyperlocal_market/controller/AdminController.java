package com.subha.hyperlocal_market.controller;

import com.subha.hyperlocal_market.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.subha.hyperlocal_market.dto.AttributeOptionDto;
import com.subha.hyperlocal_market.dto.CategoryAttributeDto;
import com.subha.hyperlocal_market.dto.CategoryDto;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/categories")
    public ResponseEntity<?> addCategory(Authentication authentication, @RequestBody CategoryDto category) {

        Long user = Long.parseLong(authentication.getName());
        return adminService.addCategory(user, category);

    }

    @PostMapping("/category-attributes")
    public ResponseEntity<?> addCategoryAttribute(
            Authentication authentication,
            @RequestBody CategoryAttributeDto dto) {

        Long user = Long.parseLong(authentication.getName());

        return adminService.addCategoryAttribute(user, dto);
    }

    @PostMapping("/attribute-options")
    public ResponseEntity<?> addAttributeOption(
            Authentication authentication,
            @RequestBody AttributeOptionDto dto) {

        Long user = Long.parseLong(authentication.getName());

        return adminService.addAttributeOption(user, dto);
    }

}
