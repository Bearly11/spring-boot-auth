package com.springproject.spring_boot_jwt.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "api/v1/users/")
public class UserController {

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.status(200).body("get success");
    }
}
