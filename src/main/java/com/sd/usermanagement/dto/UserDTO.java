package com.sd.usermanagement.dto;

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

        Set<RoleDTO> authorities
) {
}
