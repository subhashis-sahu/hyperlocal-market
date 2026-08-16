package com.subha.hyperlocal_market.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.subha.hyperlocal_market.dto.OtpRequestDto;

import com.subha.hyperlocal_market.service.AuthService;
import com.subha.hyperlocal_market.service.OtpService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final OtpService otpService;
    private final AuthService authService;

    AuthController(OtpService otpService,AuthService authService){
        this.otpService=otpService;
        this.authService=authService;
    }


    

    @PostMapping("/login")
    public ResponseEntity<?> requestOtp(@RequestBody OtpRequestDto otpRequestDto){
        return otpService.requestOtp(otpRequestDto.getPhNumber());
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequestDto otpRequestDto){
        return authService.checkAndGenerate(otpRequestDto.getOtp(),otpRequestDto.getPhNumber());

    }





    
}
