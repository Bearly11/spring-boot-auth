package com.springproject.spring_boot_jwt.dto.user;

import com.springproject.spring_boot_jwt.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserRequestDto {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 8,message = "Password must be least than 8 character")
    private String password;
    private Role role;

}
