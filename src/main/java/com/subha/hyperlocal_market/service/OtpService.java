package com.subha.hyperlocal_market.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;


import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;


import com.subha.hyperlocal_market.entity.OtpModel;

import com.subha.hyperlocal_market.repository.OtpRepo;


@Service
public class OtpService {

    private final OtpRepo otpRepo;

    

    OtpService(OtpRepo otpRepo) {
        this.otpRepo = otpRepo;
        

    }

    public int generateOtp() {

        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        return otp;
    }

    public ResponseEntity<?> requestOtp(Long phNumber) {
        OtpModel previousotp = otpRepo.findByPhNumber(phNumber);
        if (previousotp != null) {
            otpRepo.delete(previousotp);

        }
        OtpModel otpModel = new OtpModel();
        otpModel.setPhNumber(phNumber);
        otpModel.setOtp(generateOtp());
        otpModel.setStarTime(LocalDateTime.now());
        otpModel.setExperyTime(LocalDateTime.now().plusMinutes(5));
        otpModel.setVerified(false);

        otpRepo.save(otpModel);

        System.out.println(otpRepo.findByPhNumber(phNumber).getOtp());

        return ResponseEntity.ok(Map.of("message", "OTP sent successfully"));

    }

    public boolean checkOtp(int otp, Long phNumber) {

        OtpModel otpModel = otpRepo.findByPhNumber(phNumber);

        if (otpModel == null) {
            throw new RuntimeException("OTP not found for this mobile");
        }

        if (otpModel.getOtp() != otp) {
            throw new RuntimeException("Invalid OTP");
        }

        if (otpModel.getExperyTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired");
        }

        otpRepo.delete(otpModel);

        return true;
    }

}
