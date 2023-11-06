package com.sd.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.usermanagement.dto.UserDTO;
import com.sd.usermanagement.service.ApplicationUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private UserController userController;
    @Mock
    private ApplicationUserService applicationUserService;
    protected MockMvc mockMvc;

    private static String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setUp() {
        userController = new UserController(applicationUserService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    void testGetByUsername() throws Exception {
        UserDTO userDTO = new UserDTO("firstname", "lastname", "email", "username", null);
        when(applicationUserService.getByUsername("username")).thenReturn(userDTO);

        mockMvc.perform(get("/users/username/{username}", "username"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userDTO)));
    }
}