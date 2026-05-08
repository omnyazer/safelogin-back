package com.safelogin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("admin", "1234"));
    }

    public boolean register(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        users.add(new User(username, password));
        return true;
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                return true;
            }
        }

        return false;
    }
}