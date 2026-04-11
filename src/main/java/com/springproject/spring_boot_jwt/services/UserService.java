package com.springproject.spring_boot_jwt.services;


import com.springproject.spring_boot_jwt.dto.user.AdminCreateUserRequestDto;
import com.springproject.spring_boot_jwt.dto.user.RegisterUserRequestDto;
import com.springproject.spring_boot_jwt.dto.user.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    // admin only
    UserResponseDto createUser(AdminCreateUserRequestDto dto);
    List<UserResponseDto> getAllUser();
    UserResponseDto getUserById(Long id);
    UserResponseDto updateUser(AdminCreateUserRequestDto dto,Long id);
//    Page<UserResponseDto> getAllUsers(Pageable pageable);
    void deleteUser(Long id);

    //user

    UserResponseDto myProfile();
//    UserResponseDto updateMyProfile(RegisterUserRequestDto dto);


}
