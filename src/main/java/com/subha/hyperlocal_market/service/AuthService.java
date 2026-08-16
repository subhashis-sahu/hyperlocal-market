package com.subha.hyperlocal_market.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.subha.hyperlocal_market.config.JwtUtil;
import com.subha.hyperlocal_market.dto.RegisterUserDto;
import com.subha.hyperlocal_market.entity.User;
import com.subha.hyperlocal_market.repository.UsersRepo;

@Service
public class AuthService {

    private final OtpService otpService;
    private final UsersRepo usersRepo;
    private final JwtUtil jwtUtil;

    public AuthService(
            OtpService otpService,
            UsersRepo usersRepo,
            JwtUtil jwtUtil) {

        this.otpService = otpService;
        this.usersRepo = usersRepo;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> checkAndGenerate(int otp, Long phNumber) {

        
        otpService.checkOtp(otp, phNumber);

        
        User user = usersRepo.findById(phNumber).orElse(null);

        
        if (user == null) {

            user = new User();
            user.setPhNumber(phNumber);
            user.setPhNumberVerified(true);

            user = usersRepo.save(user);
        }

        
        String token = jwtUtil.generateToken(phNumber);

        
        return ResponseEntity.ok(Map.of("token", token));
    }
}
