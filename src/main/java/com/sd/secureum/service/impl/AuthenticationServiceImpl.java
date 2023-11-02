package com.sd.secureum.service.impl;

import com.sd.secureum.dto.LoginDTO;
import com.sd.secureum.dto.RegisterDTO;
import com.sd.secureum.dto.ResponseDTO;
import com.sd.secureum.dto.UserDTO;
import com.sd.secureum.mapper.RegisterMapper;
import com.sd.secureum.mapper.UserMapper;
import com.sd.secureum.model.ApplicationUser;
import com.sd.secureum.model.Role;
import com.sd.secureum.repository.RoleRepository;
import com.sd.secureum.repository.UserRepository;
import com.sd.secureum.service.AuthenticationService;
import com.sd.secureum.service.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, RegisterMapper registerMapper, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.registerMapper = registerMapper;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
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
            return new ResponseDTO(null, "Invalid username or password");
        }
    }

}
