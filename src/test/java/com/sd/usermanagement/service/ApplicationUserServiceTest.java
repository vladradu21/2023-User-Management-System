package com.sd.usermanagement.service;

import com.sd.usermanagement.dto.UserDTO;
import com.sd.usermanagement.mapper.UserMapper;
import com.sd.usermanagement.model.ApplicationUser;
import com.sd.usermanagement.repository.RoleRepository;
import com.sd.usermanagement.repository.UserRepository;
import com.sd.usermanagement.service.impl.ApplicationUserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationUserServiceTest {
    private ApplicationUserServiceImpl applicationUserService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        applicationUserService = new ApplicationUserServiceImpl(userRepository, userMapper, roleRepository);
    }

    @Test
    void testGetByUsername() {
        UserDTO userDTO = new UserDTO("firsname", "lastname", "email", "username", null);
        ApplicationUser user = new ApplicationUser("username", "password", null);
        when(userRepository.findByUsername(userDTO.username())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO foundUser = applicationUserService.getByUsername(userDTO.username());

        Assertions.assertEquals(userDTO, foundUser);
    }

    @Test
    void testGetAll() {
        UserDTO userDTO = new UserDTO("firsname", "lastname", "email", "username", null);
        ApplicationUser user = new ApplicationUser("username", "password", null);
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDTOs(List.of(user))).thenReturn(List.of(userDTO));

        List<UserDTO> foundUserDTOs = applicationUserService.getAll();

        Assertions.assertEquals(List.of(userDTO), foundUserDTOs);
    }
}