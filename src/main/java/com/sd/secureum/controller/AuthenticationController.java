package com.sd.secureum.controller;

import com.sd.secureum.dto.LoginDTO;
import com.sd.secureum.dto.RegisterDTO;
import com.sd.secureum.dto.ResponseDTO;
import com.sd.secureum.dto.UserDTO;
import com.sd.secureum.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterDTO registerDTO) {
        return authenticationService.register(registerDTO);
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginDTO loginDTO) {
        return authenticationService.login(loginDTO);
    }
}
