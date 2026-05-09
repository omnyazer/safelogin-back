package com.safelogin.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safelogin.dto.AuthRequest;
import com.safelogin.dto.AuthResponse;
import com.safelogin.entity.User;
import com.safelogin.service.JwtService;
import com.safelogin.service.UserService;

import jakarta.validation.Valid;

@RestController
@Validated
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/test")
    public String test() {
        return "Backend Java connecté";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        String username = request.getUsername().trim();
        String password = request.getPassword();

        boolean success = userService.register(username, password);
        if (!success) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new AuthResponse(false, "Nom d'utilisateur déjà utilisé."));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse(true, "Compte créé avec succès !"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        String username = request.getUsername().trim();
        String password = request.getPassword();

        Optional<User> user = userService.authenticate(username, password);
        if (user.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(false, "Identifiants incorrects."));
        }

        String token = jwtService.generateToken(username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AuthResponse(true, "Connexion réussie !", token));
    }
}
