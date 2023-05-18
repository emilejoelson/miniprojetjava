package joelsonfatima.service;


import joelsonfatima.dto.request.RoleDtoRequest;
import joelsonfatima.dto.request.UserDtoRequest;
import joelsonfatima.dto.request.UserPasswordDtoRequest;
import joelsonfatima.dto.request.UserUpdateDtoRequest;
import joelsonfatima.dto.response.UserDtoResponse;

import java.util.List;

public interface UserService {
    UserDtoResponse createUser(UserDtoRequest userDtoRequest);
    UserDtoResponse getUserById(Long userId);
    UserDtoResponse getUserByUsername(String username);
    List<UserDtoResponse> getAllUsers();
    UserDtoResponse updateUser(UserUpdateDtoRequest userDtoRequest, Long userId);
    UserDtoResponse updateRoleUser(Long userId, RoleDtoRequest roleDtoRequest);
    void updatePassword(Long userId, UserPasswordDtoRequest passwordDtoRequest);
    void deleteUser(Long userId);
    void deleteUser(String username);
}
