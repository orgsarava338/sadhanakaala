package orgsarava338.sadhanakaala.api.dto.mapper;

import org.mapstruct.Mapper;

import orgsarava338.sadhanakaala.api.dto.request.UserDTO;
import orgsarava338.sadhanakaala.api.dto.response.User;
import orgsarava338.sadhanakaala.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserDTO dto);

    User toResponse(UserEntity entity);
}
