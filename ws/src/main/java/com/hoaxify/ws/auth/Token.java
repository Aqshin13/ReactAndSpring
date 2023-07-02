package com.hoaxify.ws.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import com.hoaxify.ws.user.User;

import lombok.Data;

@Entity
@Data
public class Token {

    @Id
    private String token;

    @ManyToOne
    private User user;

}