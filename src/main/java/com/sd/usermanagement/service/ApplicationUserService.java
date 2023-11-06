package com.sd.usermanagement.service;

import com.sd.usermanagement.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplicationUserService {

    UserDTO getByUsername(String username);

    List<UserDTO> getAll();

    UserDTO update(UserDTO userDTO);

    void delete(String username);
}
