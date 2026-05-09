package com.springproject.spring_boot_jwt.controller;

import com.springproject.spring_boot_jwt.dto.auth.AuthResponseDto;
import com.springproject.spring_boot_jwt.dto.auth.RefreshTokenRequestDto;
import com.springproject.spring_boot_jwt.dto.user.LoginUserRequestDto;
import com.springproject.spring_boot_jwt.dto.user.RegisterUserRequestDto;
import com.springproject.spring_boot_jwt.dto.user.UserResponseDto;
import com.springproject.spring_boot_jwt.services.AuthService;
import com.springproject.spring_boot_jwt.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService _authService;
    private final TokenService _tokenService;


    @PostMapping("register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterUserRequestDto dto){
        var user = _authService.register(dto);
        return ResponseEntity.status(201).body(user);
    }
    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginUserRequestDto dto){
        var user = _authService.login(dto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("refresh")
    public ResponseEntity<AuthResponseDto> refresh(@RequestBody RefreshTokenRequestDto dto){
        return ResponseEntity.ok(_authService.refreshToken(dto));
    }
    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.ok("No token provide");
        }
        String jwt = authHeader.substring(7);
        _tokenService.revokeToken(jwt);
        return ResponseEntity.ok("Logged out Successfully");
    }
}
