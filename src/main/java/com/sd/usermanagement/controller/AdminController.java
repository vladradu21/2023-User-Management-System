package com.sd.usermanagement.controller;

import com.sd.usermanagement.dto.UserDTO;
import com.sd.usermanagement.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    private final ApplicationUserService applicationUserService;

    @Autowired
    public AdminController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/all")
    public List<UserDTO> getAll() {
        return applicationUserService.getAll();
    }

    @PutMapping("/update")
    public UserDTO update(@RequestBody UserDTO userDTO) {
        return applicationUserService.update(userDTO);
    }

    @DeleteMapping("/delete/{username}")
    public void delete(@PathVariable String username) {
        applicationUserService.delete(username);
    }
}
