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

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private  AdminController adminController;
    @Mock
    private ApplicationUserService applicationUserService;
    private MockMvc mockMvc;

    private static String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setUp() {
        adminController = new AdminController(applicationUserService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .build();
    }

    @Test
    void testGetAll() throws Exception {
        List<UserDTO> userDTOList = List.of(
                new UserDTO("firstname1", "lastname1", "email1", "username1", null),
                new UserDTO("firstname2", "lastname2", "email2", "username2", null)
        );
        when(applicationUserService.getAll()).thenReturn(userDTOList);

        mockMvc.perform(get("/admin/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userDTOList)));
    }
}