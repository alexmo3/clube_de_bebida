package com.clubedebebida.backend.service.impl;

import com.clubedebebida.backend.model.User;
import com.clubedebebida.backend.repository.UserRepository;
import com.clubedebebida.backend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
