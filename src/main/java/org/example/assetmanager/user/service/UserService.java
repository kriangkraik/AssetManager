package org.example.assetmanager.user.service;

import org.example.assetmanager.user.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUserId(Long userId);

}
