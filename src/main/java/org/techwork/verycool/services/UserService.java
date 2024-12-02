package org.techwork.verycool.services;

import org.techwork.verycool.models.entities.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll();
    UserEntity findUserById(Long id);
    List<UserEntity> findUsers();
}