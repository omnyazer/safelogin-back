package com.safelogin;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private UserService userService = new UserService();

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        boolean success = userService.register(request.getUsername(), request.getPassword());

        if (success) {
            return "Compte créé avec succès !";
        }

        return "Impossible de créer le compte.";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        boolean success = userService.login(request.getUsername(), request.getPassword());

        if (success) {
            return "Connexion réussie !";
        }

        return "Identifiants incorrects.";
    }
}