package com.subha.hyperlocal_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.subha.hyperlocal_market.entity.User;



@Repository
public interface UsersRepo extends JpaRepository<User ,Long> {

    




    
}
