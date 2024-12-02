package org.techwork.verycool.services.impl;

import org.springframework.stereotype.Service;
import org.techwork.verycool.exceptions.UserNotFoundException;
import org.techwork.verycool.models.entities.UserEntity;
import org.techwork.verycool.repositories.UserRepository;
import org.techwork.verycool.services.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found or does not exist"));
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserEntity> findUsers() {
        return userRepository.findAll();
    }
}
