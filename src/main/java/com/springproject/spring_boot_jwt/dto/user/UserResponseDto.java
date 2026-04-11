package com.springproject.spring_boot_jwt.dto.user;

import com.springproject.spring_boot_jwt.enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String username;
    private Role role;
}
