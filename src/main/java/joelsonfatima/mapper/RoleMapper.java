package joelsonfatima.mapper;

import joelsonfatima.dto.request.RoleDtoRequest;
import joelsonfatima.dto.response.RoleDtoResponse;
import joelsonfatima.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    Role roleDtoRequestToRole(RoleDtoRequest roleDtoRequest);
    RoleDtoResponse roleToRoleDtoResponse(Role role);
}
