package com.org.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.dto.AuthRequest;
import com.org.dto.AuthResponse;
import com.org.entity.User;
import com.org.repository.TokenRepository;
import com.org.repository.UserRepository;
import com.org.security.CustomUserDetailsService;
import com.org.security.JwtUtil;
import com.org.security.TokenService;
import com.org.util.RefreshTokenRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public AuthController(AuthenticationManager authManager, CustomUserDetailsService userDetailsService,
                          JwtUtil jwtUtil, TokenService tokenService, UserRepository userRepository,TokenRepository tokenRepository) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.tokenRepository=tokenRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        tokenService.revokeAllUserTokens(user.getUsername());

        String accessToken = jwtUtil.generateToken(user.getUsername(), 5 * 60 * 1000); // 5 min
        String refreshToken = jwtUtil.generateToken(user.getUsername(), 30 * 60 * 1000); // 30 min

        tokenService.saveUserToken(user, accessToken);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest req) {
        String username = jwtUtil.extractUsername(req.getRefreshToken());

        if (!jwtUtil.isTokenValid(req.getRefreshToken()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = userRepository.findByUsername(username).orElseThrow();

        String accessToken = jwtUtil.generateToken(username, 5 * 60 * 1000);
        tokenService.revokeAllUserTokens(username);
        tokenService.saveUserToken(user, accessToken);

        return ResponseEntity.ok(new AuthResponse(accessToken, req.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenRepository.findByToken(token).ifPresent(t -> {
                t.setRevoked(true);
                t.setExpired(true);
                tokenRepository.save(t);
            });
        }
        return ResponseEntity.ok().build();
    }
}
