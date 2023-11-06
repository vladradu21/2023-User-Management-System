package com.sd.usermanagement.mapper;

import com.sd.usermanagement.dto.RegisterDTO;
import com.sd.usermanagement.model.ApplicationUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    ApplicationUser toEntity(RegisterDTO registerDTO);
}
