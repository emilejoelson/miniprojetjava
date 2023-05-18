package joelsonfatima.mapper;

import joelsonfatima.dto.request.RegisterDtoRequest;
import joelsonfatima.dto.request.UserDtoRequest;
import joelsonfatima.dto.response.UserDtoResponse;
import joelsonfatima.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User userDtoRequestToUser(UserDtoRequest userDtoRequest);
    User registerDtoRequestToUser(RegisterDtoRequest registerDtoRequest);
    UserDtoResponse userToUserDtoResponse(User user);
}
