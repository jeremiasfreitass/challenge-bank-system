package com.challange.bank.system.service;

import com.challange.bank.system.dto.UserDTO;
import com.challange.bank.system.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    Optional<User> getUser(Long userId);
    List<UserDTO> getAllUsers();
}
