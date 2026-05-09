package com.safelogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safelogin.entity.User;
import com.safelogin.entity.UserRole;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByRole(UserRole role);
}
