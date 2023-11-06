package com.sd.usermanagement.dto;

public record ResponseDTO(
        UserDTO user,

        String token
) {
}
