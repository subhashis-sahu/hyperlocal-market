package com.subha.hyperlocal_market.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subha.hyperlocal_market.config.JwtUtil;
import com.subha.hyperlocal_market.dto.UsersProfileDto;
import com.subha.hyperlocal_market.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    UserController(JwtUtil jwtUtil,UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService=userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UsersProfileDto> getProfileInfo(
            Authentication authentication) {

        return ResponseEntity.ok(userService.profile(authentication));
    }

    


    

}
