package com.org.security;

import java.util.List;

import org.springframework.stereotype.Service;

import com.org.entity.Token;
import com.org.entity.User;
import com.org.repository.TokenRepository;

@Service
public class TokenService {

    private final TokenRepository tokenRepo;

    public TokenService(TokenRepository tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public void saveUserToken(User user, String tokenStr) {
        Token token = new Token();
        token.setToken(tokenStr);
        token.setUser(user);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepo.save(token);
    }

    public void revokeAllUserTokens(String username) {
        List<Token> tokens = tokenRepo.findAllByUser_UsernameAndExpiredFalseAndRevokedFalse(username);
        for (Token token : tokens) {
            token.setExpired(true);
            token.setRevoked(true);
        }
        tokenRepo.saveAll(tokens);
    }
}
