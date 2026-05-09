package com.springproject.spring_boot_jwt.services.impl;

import com.springproject.spring_boot_jwt.dto.auth.AuthResponseDto;
import com.springproject.spring_boot_jwt.dto.auth.RefreshTokenRequestDto;
import com.springproject.spring_boot_jwt.dto.user.LoginUserRequestDto;
import com.springproject.spring_boot_jwt.dto.user.RegisterUserRequestDto;
import com.springproject.spring_boot_jwt.dto.user.UserResponseDto;
import com.springproject.spring_boot_jwt.enums.Role;
import com.springproject.spring_boot_jwt.exceptions.AuthException;
import com.springproject.spring_boot_jwt.exceptions.DuplicateException;
import com.springproject.spring_boot_jwt.exceptions.UnauthorizedException;
import com.springproject.spring_boot_jwt.mappers.user.UserMapper;
import com.springproject.spring_boot_jwt.repositories.UserRepository;
import com.springproject.spring_boot_jwt.security.jwt.JwtService;
import com.springproject.spring_boot_jwt.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.springproject.spring_boot_jwt.services.TokenService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository _userRepository;
    private final UserMapper _userMapper;
    private final PasswordEncoder _passwordEncoder;
    private final AuthenticationManager _authenticationManager;
    private final JwtService _jwtService;
    private final UserDetailsService _userDetailService;
    private final TokenService _tokenService;


    @Override
    public UserResponseDto register(RegisterUserRequestDto dto) {
        if(_userRepository.existsByUsername(dto.getUsername())){
            throw new DuplicateException("Username is already exists");
        }
        var user = _userMapper.toEntity(dto);
        user.setPassword(_passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);
        return _userMapper.toDto(_userRepository.save(user));
    }

    @Override
    public AuthResponseDto login(LoginUserRequestDto dto) {
        try{
            var authentication = _authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );

            var user =(UserDetails) authentication.getPrincipal();
            _tokenService.revokeAllUser(user);


            String token = _jwtService.generateAccessToken(user);
            String refreshToken = _jwtService.generateRefreshToken(user);


            _tokenService.saveUserToken(user,token);
            _tokenService.saveUserToken(user,refreshToken);

            return new AuthResponseDto(
                    token,
                    refreshToken,
                    user.getUsername(),
                    user.getAuthorities().toString()
            );

        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Invalid username or password");
        }

    }

    @Override
    public AuthResponseDto refreshToken(RefreshTokenRequestDto dto) {
        String refreshToken = dto.getRefreshToken();
        String username = _jwtService.extractUsername(refreshToken);
        UserDetails user = _userDetailService.loadUserByUsername(username);

        if(!_jwtService.isTokenValid(refreshToken,user)){
            throw new UnauthorizedException("Invalid refresh token");
        }
        String newAccessToken = _jwtService.generateAccessToken(user);
        String newRefreshToken = _jwtService.generateRefreshToken(user);
        _tokenService.saveUserToken(user,newAccessToken);
        _tokenService.saveUserToken(user,newRefreshToken);
        return new AuthResponseDto(
                newAccessToken,
                newRefreshToken,
                user.getUsername(),
                user.getAuthorities().toString()
        );
    }
}
