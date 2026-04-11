package com.springproject.spring_boot_jwt.services.auth;

import com.springproject.spring_boot_jwt.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository _userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException{

        var user = _userRepository.findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException("user not found username: "+username));
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

    }
}
