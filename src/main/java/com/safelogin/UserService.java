package com.safelogin;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }

        User existingUser = userRepository.findByUsername(username);

        if (existingUser != null) {
            return false;
        }

        User newUser = new User(username, password);
        userRepository.save(newUser);

        return true;
    }

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return false;
        }

        return user.checkPassword(password);
    }
}