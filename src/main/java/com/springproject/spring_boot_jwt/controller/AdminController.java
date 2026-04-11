package com.springproject.spring_boot_jwt.controller;

import com.springproject.spring_boot_jwt.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/")
public class AdminController {

    @GetMapping()
    public String admin(){
        return "Admin ";
    }



}
