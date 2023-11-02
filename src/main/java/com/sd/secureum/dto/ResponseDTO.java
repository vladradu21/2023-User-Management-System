package com.sd.secureum.dto;

public record ResponseDTO(
        UserDTO user,

        String token
) {
}
