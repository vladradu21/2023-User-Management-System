package com.sd.secureum.dto;

import com.sd.secureum.model.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserDTO(
        @NotNull
        String firstname,

        @NotNull
        String lastname,

        @NotNull
        String email,

        @Size(min = 3, max = 20)
        @NotNull
        String username,

        Set<Role> authorities
) {
}
