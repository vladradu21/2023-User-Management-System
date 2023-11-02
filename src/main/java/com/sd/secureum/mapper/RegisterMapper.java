package com.sd.secureum.mapper;

import com.sd.secureum.dto.RegisterDTO;
import com.sd.secureum.model.ApplicationUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    ApplicationUser toEntity(RegisterDTO registerDTO);
}
