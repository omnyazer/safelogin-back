package com.safelogin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            return false;
        }

        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, hashedPassword);
        userRepository.save(newUser);

        return true;
    }

    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(password, user.getPassword());
    }
}
