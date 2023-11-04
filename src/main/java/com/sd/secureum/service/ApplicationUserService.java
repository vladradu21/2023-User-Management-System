package com.sd.secureum.service;

import com.sd.secureum.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplicationUserService {

    UserDTO getByUsername(String username);

    List<UserDTO> getAll();

    void update(UserDTO userDTO);

    void delete(String username);
}
