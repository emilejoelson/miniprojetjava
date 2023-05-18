package joelsonfatima.service.impl;

import joelsonfatima.auth.AuthenticationFacade;
import joelsonfatima.dto.ROLES;
import joelsonfatima.dto.request.RoleDtoRequest;
import joelsonfatima.dto.request.UserDtoRequest;
import joelsonfatima.dto.request.UserPasswordDtoRequest;
import joelsonfatima.dto.request.UserUpdateDtoRequest;
import joelsonfatima.dto.response.UserDtoResponse;
import joelsonfatima.entity.Role;
import joelsonfatima.entity.User;
import joelsonfatima.exception.AppException;
import joelsonfatima.exception.ResourceNotFoundException;
import joelsonfatima.mapper.UserMapper;
import joelsonfatima.repository.RoleRepository;
import joelsonfatima.repository.UserRepository;
import joelsonfatima.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;
    @Override
    @Transactional
    public UserDtoResponse createUser(UserDtoRequest userDtoRequest) {
        // check if already exist in db
        if(userRepository.existsByUsername(userDtoRequest.username())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "User already exist");
        }

        // convert DTO to entity
        User user = UserMapper.INSTANCE.userDtoRequestToUser(userDtoRequest);

        // encode password
        user.setPassword(passwordEncoder.encode(userDtoRequest.password()));

        // add roles to user entity
        if(userDtoRequest.role() == null || !StringUtils.hasText(userDtoRequest.role().name())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "should add role to user");
        }

        addRoleToUser(user, userDtoRequest.role().name());

        // save user to db
        User savedUser = userRepository.save(user);

        return UserMapper.INSTANCE.userToUserDtoResponse(savedUser);
    }

    @Override
    public UserDtoResponse getUserById(Long userId) {
        // Get user from database
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId.toString())
        );
        return UserMapper.INSTANCE.userToUserDtoResponse(user);
    }

    @Override
    public UserDtoResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username)
        );

        return UserMapper.INSTANCE.userToUserDtoResponse(user);
    }
    @Override
    public List<UserDtoResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper.INSTANCE::userToUserDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserDtoResponse updateUser(UserUpdateDtoRequest userDtoRequest, Long userId) {
        // get user by id from db
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId.toString())
        );

        // check permission before update
        if(!(isAdmin() || isLoggedUser(user)))
            throw new AppException(HttpStatus.FORBIDDEN, "Access denied");

        if(StringUtils.hasText(userDtoRequest.username())) {
            user.setUsername(userDtoRequest.username());
        }

        // update user in db
        User updatedUser = userRepository.save(user);

        return UserMapper.INSTANCE.userToUserDtoResponse(updatedUser);
    }

    @Override
    public void updatePassword(Long userId, UserPasswordDtoRequest passwordDtoRequest) {
        // Get user from database
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId.toString())
        );

        // check logged user
        if(!(isAdmin() || isLoggedUser(user)))
            throw new AppException(HttpStatus.FORBIDDEN, "Access denied");

        if(!passwordDtoRequest.newPassword().equals(passwordDtoRequest.confirmedNewPassword()))
            throw new AppException(HttpStatus.BAD_REQUEST, "The confirmed password does not match the new password");

        user.setPassword(passwordEncoder.encode(passwordDtoRequest.newPassword()));

        // Update user in database
        userRepository.save(user);
    }

    @Override
    public UserDtoResponse updateRoleUser(Long userId, RoleDtoRequest roleDtoRequest) {
        // Get user from database
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId.toString())
        );

        addRoleToUser(user, roleDtoRequest.name());

        // Update user in database
        User updatedUser = userRepository.save(user);

        return UserMapper.INSTANCE.userToUserDtoResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        // get user by id from db
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId.toString())
        );
        userRepository.delete(user);
    }

    @Override
    public void deleteUser(String username) {
        // get user by id from db
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username)
        );
        userRepository.delete(user);
    }

    private void addRoleToUser(User user, String name) {
        // get role from db
        Role role = roleRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", name)
        );

        // check if admin before add admin role
        if(role.getName().equals(ROLES.ROLE_ADMIN.name()) && !isAdmin())
            throw new AppException(HttpStatus.FORBIDDEN, "Don't have permission to create user with role admin");

        // add role to user
        user.setRole(role);
    }

    private boolean isAdmin() {
        String username = authenticationFacade.getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username)
        );
        return user.getRole().getName().equals(ROLES.ROLE_ADMIN.name());
    }

    private boolean isLoggedUser(User user) {
        String username = authenticationFacade.getAuthentication().getName();
        return user.getUsername().equals(username);
    }

}
