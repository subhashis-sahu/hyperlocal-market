package com.subha.hyperlocal_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.subha.hyperlocal_market.entity.OtpModel;



@Repository
public interface OtpRepo extends JpaRepository<OtpModel,Integer>{
    OtpModel findByPhNumber(Long phNumber);

    

    
}
