package com.springproject.spring_boot_jwt.services.impl;

import com.springproject.spring_boot_jwt.exceptions.UnauthorizedException;
import com.springproject.spring_boot_jwt.models.Token;
import com.springproject.spring_boot_jwt.models.User;
import com.springproject.spring_boot_jwt.repositories.TokenRepository;
import com.springproject.spring_boot_jwt.repositories.UserRepository;
import com.springproject.spring_boot_jwt.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository _tokenRepository;
    private final UserRepository _userRepository;

    @Override
    public void saveUserToken(UserDetails userDetails, String jwtToken) {
        String username = userDetails.getUsername();
        User user = _userRepository.findByUsername(username)
                .orElseThrow();
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setExpired(false);
        token.setRevoked(false);

        _tokenRepository.save(token);
    }

    @Override
    public void revokeAllUser(UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = _userRepository.findByUsername(username)
                .orElseThrow();
        var validToken = _tokenRepository
                .findAllByUserIdAndExpiredFalseAndRevokedFalse(user.getId());

        if(validToken.isEmpty()) return;

        validToken.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        _tokenRepository.saveAll(validToken);

    }

    @Override
    public boolean isTokenValid(String token) {
        return _tokenRepository.findByToken(token)
                .map(t-> !t.isExpired() && !t.isRevoked())
                .orElse(false);
    }

    @Override
    public void revokeToken(String token) {
        var storedToken = _tokenRepository.findByToken(token)
                .orElseThrow(()->
                        new UnauthorizedException("Token not found"));
        storedToken.setExpired(true);
        storedToken.setRevoked(true);
        _tokenRepository.save(storedToken);
    }
}
