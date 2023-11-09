package com.sd.usermanagement.service.impl;

import com.sd.usermanagement.dto.LoginDTO;
import com.sd.usermanagement.dto.RegisterDTO;
import com.sd.usermanagement.dto.ResponseDTO;
import com.sd.usermanagement.dto.UserDTO;
import com.sd.usermanagement.exceptions.UmConflictException;
import com.sd.usermanagement.mapper.RegisterMapper;
import com.sd.usermanagement.mapper.UserMapper;
import com.sd.usermanagement.model.ApplicationUser;
import com.sd.usermanagement.model.Role;
import com.sd.usermanagement.repository.RoleRepository;
import com.sd.usermanagement.repository.UserRepository;
import com.sd.usermanagement.service.AuthenticationService;
import com.sd.usermanagement.service.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RegisterMapper registerMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final WebClient webClient;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, RegisterMapper registerMapper, AuthenticationManager authenticationManager, TokenService tokenService, WebClient webClient) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.registerMapper = registerMapper;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.webClient = webClient;
    }


    @Override
    public UserDTO register(RegisterDTO registerDTO) {
        ApplicationUser userToSave = registerMapper.toEntity(registerDTO);

        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));

        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        userToSave.setAuthorities(authorities);

        ApplicationUser savedUser = userRepository.save(userToSave);

        webClient.post()
                .uri("/users/" + savedUser.getUsername())
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        return userMapper.toDTO(savedUser);
    }

    @Override
    public ResponseDTO login(LoginDTO loginDTO) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password())
            );

            String token = tokenService.generateJwt(auth);
            ApplicationUser user = userRepository.findByUsername(loginDTO.username()).get();
            return new ResponseDTO(userMapper.toDTO(user), token);

        } catch (AuthenticationException e) {
            throw new UmConflictException("Invalid username/password supplied");
        }
    }

}
