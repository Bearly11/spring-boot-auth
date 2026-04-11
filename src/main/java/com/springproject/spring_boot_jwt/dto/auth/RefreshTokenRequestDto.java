package com.springproject.spring_boot_jwt.dto.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenRequestDto {
    private String refreshToken;
}
