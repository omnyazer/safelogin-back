package com.safelogin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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

        if (success) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new AuthResponse(true, "Compte créé avec succès !"));
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new AuthResponse(false, "Nom d'utilisateur déjà utilisé."));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        String username = request.getUsername().trim();
        String password = request.getPassword();

        boolean success = userService.login(username, password);

        if (success) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new AuthResponse(true, "Connexion réussie !"));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse(false, "Identifiants incorrects."));
    }
}
