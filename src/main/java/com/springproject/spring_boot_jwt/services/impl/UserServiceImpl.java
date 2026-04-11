package com.springproject.spring_boot_jwt.services.impl;

import com.springproject.spring_boot_jwt.dto.user.AdminCreateUserRequestDto;
import com.springproject.spring_boot_jwt.dto.user.UserResponseDto;
import com.springproject.spring_boot_jwt.exceptions.DuplicateException;
import com.springproject.spring_boot_jwt.exceptions.NotFoundException;
import com.springproject.spring_boot_jwt.mappers.user.UserMapper;
import com.springproject.spring_boot_jwt.models.User;
import com.springproject.spring_boot_jwt.repositories.UserRepository;
import com.springproject.spring_boot_jwt.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository _userRepository;
    private final PasswordEncoder _passwordEncoder;
    private final UserMapper _userMapper;

    private User getCurrentUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return _userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user"));
    }
    // Admin service
    @Override
    public UserResponseDto createUser(AdminCreateUserRequestDto dto){
        if(_userRepository.existsByUsername(dto.getUsername())){
            throw new DuplicateException("Duplicate username");
        }
        var user = _userMapper.toEntity(dto);
        user.setPassword(_passwordEncoder.encode(dto.getPassword()));
        return _userMapper.toDto(_userRepository.save(user));

    }
    @Override
    public List<UserResponseDto> getAllUser(){
       var users = _userRepository.findAll()
               .stream()
               .map(_userMapper::toDto)
               .toList();
       return users;
    }
    @Override
    public UserResponseDto getUserById(Long id){
        var user = _userRepository.findById(id)
                .orElseThrow(()->
                        new NotFoundException("Id not found"));
        return _userMapper.toDto(user);
    }
    @Override
    public UserResponseDto updateUser(AdminCreateUserRequestDto dto,Long id){
        var user = _userRepository.findById(id)
                .orElseThrow(()->
                        new NotFoundException("user not found"));
        if(_userRepository.existsByUsernameAndIdNot(dto.getUsername(),id)){
            throw new NotFoundException("User not found");
        }
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());

        return _userMapper.toDto(_userRepository.save(user));
    }
    //user service
    @Override
    public UserResponseDto myProfile(){
        var user = getCurrentUser();
        return _userMapper.toDto(user);

    }

    @Override
    public void deleteUser(Long id) {
        if(!_userRepository.existsById(id)){
            throw new NotFoundException("Not found id");
        }
        _userRepository.deleteById(id);
    }
}
