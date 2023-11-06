package com.sd.usermanagement.service;

import com.sd.usermanagement.dto.LoginDTO;
import com.sd.usermanagement.dto.RegisterDTO;
import com.sd.usermanagement.dto.ResponseDTO;
import com.sd.usermanagement.dto.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface AuthenticationService {
    UserDTO register(RegisterDTO registerDTO);

    ResponseDTO login(LoginDTO loginDTO);
}
