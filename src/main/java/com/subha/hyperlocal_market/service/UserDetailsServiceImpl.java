package com.subha.hyperlocal_market.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.subha.hyperlocal_market.entity.User;
import com.subha.hyperlocal_market.repository.UsersRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    
    private final UsersRepo usersRepo;

    UserDetailsServiceImpl(UsersRepo usersRepo){
        this.usersRepo=usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {

        Long phNumber = Long.parseLong(number);

        User user = usersRepo.findById(phNumber).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getPhNumber().toString())
                .build();
    }
}