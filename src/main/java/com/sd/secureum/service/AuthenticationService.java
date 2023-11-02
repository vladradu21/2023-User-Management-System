package com.sd.secureum.service;

import com.sd.secureum.dto.LoginDTO;
import com.sd.secureum.dto.RegisterDTO;
import com.sd.secureum.dto.ResponseDTO;
import com.sd.secureum.dto.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface AuthenticationService {
    UserDTO register(RegisterDTO registerDTO);

    ResponseDTO login(LoginDTO loginDTO);
}
