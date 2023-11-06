package com.sd.usermanagement.mapper;

import com.sd.usermanagement.dto.UserDTO;
import com.sd.usermanagement.model.ApplicationUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(ApplicationUser user);

    List<UserDTO> toDTOs(List<ApplicationUser> users);

    ApplicationUser toEntity(UserDTO userDTO);

    List<ApplicationUser> toEntities(List<UserDTO> userDTOs);
}
