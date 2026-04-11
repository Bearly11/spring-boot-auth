package com.springproject.spring_boot_jwt.mappers.user;

import com.springproject.spring_boot_jwt.dto.user.AdminCreateUserRequestDto;
import com.springproject.spring_boot_jwt.dto.user.RegisterUserRequestDto;
import com.springproject.spring_boot_jwt.dto.user.UserResponseDto;
import com.springproject.spring_boot_jwt.models.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);


    User toEntity(RegisterUserRequestDto dto);

    User toEntity(AdminCreateUserRequestDto dto);

}
