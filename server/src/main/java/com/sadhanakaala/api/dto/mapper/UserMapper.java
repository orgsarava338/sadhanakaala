package com.sadhanakaala.api.dto.mapper;

import org.mapstruct.Mapper;

import com.sadhanakaala.api.dto.request.UserDTO;
import com.sadhanakaala.api.dto.response.User;
import com.sadhanakaala.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserDTO dto);

    User toResponse(UserEntity entity);
}
