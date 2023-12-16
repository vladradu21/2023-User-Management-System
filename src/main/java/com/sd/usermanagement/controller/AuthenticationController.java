package com.sd.usermanagement.controller;

import com.sd.usermanagement.dto.*;
import com.sd.usermanagement.service.AuthenticationService;
import com.sd.usermanagement.utils.RSAKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final RSAKeyProperties keys;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, RSAKeyProperties keys) {
        this.authenticationService = authenticationService;
        this.keys = keys;
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterDTO registerDTO) {
        return authenticationService.register(registerDTO);
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginDTO loginDTO) {
        return authenticationService.login(loginDTO);
    }

    @GetMapping("/publicKey")
    public ResponseEntity<RSAPublicKeyDTO> getPublicKey() {
        RSAPublicKey publicKey = keys.getPublicKey();
        if(publicKey != null) {
            String modulus = Base64.getEncoder().encodeToString(publicKey.getModulus().toByteArray());
            String exponent = Base64.getEncoder().encodeToString(publicKey.getPublicExponent().toByteArray());
            RSAPublicKeyDTO publicKeyDTO = new RSAPublicKeyDTO(modulus, exponent);
            return ResponseEntity.ok(publicKeyDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
