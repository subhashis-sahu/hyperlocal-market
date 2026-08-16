package com.subha.hyperlocal_market.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    

    @Id
    private Long phNumber;

    

    
    private boolean phNumberVerified=false;


    private String name;


    private String address;

    
    private LocalDateTime createdAt; 
    private LocalDateTime updatedAt;  

    @OneToOne(mappedBy = "user")
    private UserLocation location;
    
}
