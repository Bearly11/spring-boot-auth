package com.springproject.spring_boot_jwt.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
    void saveUserToken(UserDetails user,String token);
    void revokeAllUser(UserDetails user);
    void revokeToken(String token);
    boolean isTokenValid(String token);
}
