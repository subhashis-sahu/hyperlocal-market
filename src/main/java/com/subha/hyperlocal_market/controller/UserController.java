package com.subha.hyperlocal_market.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subha.hyperlocal_market.config.JwtUtil;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final JwtUtil jwtUtil;

    UserController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfileInfo(
            Authentication authentication) {

        String phoneNumber = authentication.getName();

        return ResponseEntity.ok(phoneNumber);
    }


    

}
