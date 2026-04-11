package com.springproject.spring_boot_jwt.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponseDto {
    private String token;
    private String refreshToken;
    private String username;
    private String role;

}
