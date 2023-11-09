package com.sd.usermanagement.service.impl;

import com.sd.usermanagement.dto.RoleDTO;
import com.sd.usermanagement.dto.UserDTO;
import com.sd.usermanagement.exceptions.UmNotFoundException;
import com.sd.usermanagement.mapper.UserMapper;
import com.sd.usermanagement.model.ApplicationUser;
import com.sd.usermanagement.model.Role;
import com.sd.usermanagement.repository.RoleRepository;
import com.sd.usermanagement.repository.UserRepository;
import com.sd.usermanagement.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final WebClient webClient;

    @Autowired
    public ApplicationUserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, WebClient webClient) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.webClient = webClient;
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
    public UserDTO update(@RequestBody UserDTO userDTO) {
        ApplicationUser user = userRepository.findByUsername(userDTO.username())
                .orElseThrow(() -> new UmNotFoundException("User not found"));

        user.setFirstname(userDTO.firstname());
        user.setLastname(userDTO.lastname());
        user.setEmail(userDTO.email());

        Set<Role> updatedRoles = new HashSet<>();
        for(RoleDTO roleDTO : userDTO.authorities()) {
            Role role = roleRepository.findByAuthority(roleDTO.authority())
                    .orElseThrow(() -> new UmNotFoundException("Role not found"));
            updatedRoles.add(role);
        }
        user.setAuthorities(updatedRoles);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public void delete(String username) {
        userRepository.findByUsername(username).ifPresent(userRepository::delete);

        webClient.delete()
                .uri("users/delete/" + username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
