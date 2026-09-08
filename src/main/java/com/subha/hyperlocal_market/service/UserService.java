package com.subha.hyperlocal_market.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.subha.hyperlocal_market.dto.UsersProfileDto;
import com.subha.hyperlocal_market.entity.User;
import com.subha.hyperlocal_market.repository.UsersRepo;

@Service 
public class UserService {
    private final UsersRepo usersRepo;
    public UserService(UsersRepo usersRepo){
        this.usersRepo=usersRepo;
    }

    public UsersProfileDto profile(Authentication authentication){
        Long name=Long.parseLong(authentication.getName());
        User exitingUser=usersRepo.findById(name).orElseThrow();
        UsersProfileDto user=new UsersProfileDto();
        user.setName(exitingUser.getName());
        user.setPhNumber(exitingUser.getPhNumber());
        return user;
    }
    
    
}
