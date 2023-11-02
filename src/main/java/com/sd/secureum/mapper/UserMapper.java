package com.sd.secureum.mapper;

import com.sd.secureum.dto.UserDTO;
import com.sd.secureum.model.ApplicationUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(ApplicationUser user);

    List<UserDTO> toDTOs(List<ApplicationUser> users);

    ApplicationUser toEntity(UserDTO userDTO);

    List<ApplicationUser> toEntities(List<UserDTO> userDTOs);
}
