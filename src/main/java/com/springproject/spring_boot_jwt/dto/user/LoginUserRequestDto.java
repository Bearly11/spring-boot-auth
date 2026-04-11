package com.springproject.spring_boot_jwt.dto.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginUserRequestDto {
    private String username;
    private String password;
}
