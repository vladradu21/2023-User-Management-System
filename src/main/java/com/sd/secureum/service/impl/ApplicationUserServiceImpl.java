package com.sd.secureum.service.impl;

import com.sd.secureum.dto.UserDTO;
import com.sd.secureum.exceptions.UmNotFoundException;
import com.sd.secureum.mapper.UserMapper;
import com.sd.secureum.repository.UserRepository;
import com.sd.secureum.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public ApplicationUserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new UmNotFoundException("User not found"));
    }

    @Override
    public List<UserDTO> getAll() {
        return userMapper.toDTOs(userRepository.findAll());
    }

    @Override
    public void update(UserDTO userDTO) {
        userRepository.findByUsername(userDTO.username()).ifPresent(user -> {
            user.setFirstname(userDTO.firstname());
            user.setLastname(userDTO.lastname());
            user.setEmail(userDTO.email());
            user.setAuthorities(userDTO.authorities());
            userRepository.save(user);
        });
    }

    @Override
    public void delete(String username) {
        userRepository.findByUsername(username).ifPresent(userRepository::delete);
    }
}
