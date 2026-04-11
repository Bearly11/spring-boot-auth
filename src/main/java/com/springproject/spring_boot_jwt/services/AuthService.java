package com.springproject.spring_boot_jwt.services;


import com.springproject.spring_boot_jwt.dto.auth.AuthResponseDto;
import com.springproject.spring_boot_jwt.dto.auth.RefreshTokenRequestDto;
import com.springproject.spring_boot_jwt.dto.user.LoginUserRequestDto;
import com.springproject.spring_boot_jwt.dto.user.RegisterUserRequestDto;
import com.springproject.spring_boot_jwt.dto.user.UserResponseDto;

public interface AuthService {

    UserResponseDto register(RegisterUserRequestDto dto);
    AuthResponseDto login(LoginUserRequestDto dto);
    AuthResponseDto refreshToken(RefreshTokenRequestDto dto);

}
