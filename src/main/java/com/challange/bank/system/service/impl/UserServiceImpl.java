package com.challange.bank.system.service.impl;

import com.challange.bank.system.dto.UserDTO;
import com.challange.bank.system.model.User;
import com.challange.bank.system.repository.UserRepository;
import com.challange.bank.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDTO.class);
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return Collections.singletonList(mapper.map(users, UserDTO.class));
    }
}
