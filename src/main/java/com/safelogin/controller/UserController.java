package com.safelogin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safelogin.dto.AuthResponse;
import com.safelogin.dto.ChangePasswordRequest;
import com.safelogin.dto.UserResponse;
import com.safelogin.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, String>> dashboard() {
        return ResponseEntity.ok(Map.of("message", "Route dashboard protégée."));
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String, String>> profile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Authentification requise."));
        }

        return ResponseEntity.ok(Map.of(
                "username", userDetails.getUsername(),
                "message", "Profil récupéré avec succès."));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<AuthResponse> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new AuthResponse(true, "Utilisateur supprimé avec succès."));
    }

    @PutMapping("/change-password")
    public ResponseEntity<AuthResponse> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequest request) {
        if (userDetails == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(false, "Authentification requise."));
        }

        userService.changePassword(userDetails.getUsername(), request);
        return ResponseEntity.ok(new AuthResponse(true, "Mot de passe modifié avec succès."));
    }
}
