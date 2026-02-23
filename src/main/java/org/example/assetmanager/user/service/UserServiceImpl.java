package org.example.assetmanager.user.service;

import lombok.RequiredArgsConstructor;
import org.example.assetmanager.user.entity.User;
import org.example.assetmanager.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;

    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }


}
