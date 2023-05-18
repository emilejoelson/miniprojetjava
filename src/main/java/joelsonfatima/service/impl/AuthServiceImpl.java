package joelsonfatima.service.impl;

import joelsonfatima.auth.IAuthenticationFacade;
import joelsonfatima.auth.JwtTokenProvider;
import joelsonfatima.dto.ROLES;
import joelsonfatima.dto.request.LoginRequestDto;
import joelsonfatima.dto.request.RegisterDtoRequest;
import joelsonfatima.dto.request.ResetPasswordDtoRequest;
import joelsonfatima.entity.Role;
import joelsonfatima.entity.User;
import joelsonfatima.exception.AppException;
import joelsonfatima.exception.ResourceNotFoundException;
import joelsonfatima.mapper.UserMapper;
import joelsonfatima.repository.RoleRepository;
import joelsonfatima.repository.UserRepository;
import joelsonfatima.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final IAuthenticationFacade authenticationFacade;
    @Override
    public String login(LoginRequestDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.username(), loginDto.password()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

    @Override
    @Transactional
    public String register(RegisterDtoRequest registerDtoRequest) {
        // Check if user already exist
        if(userRepository.existsByUsername(registerDtoRequest.username()))
            throw new AppException(HttpStatus.BAD_REQUEST, "We have this username allready");

        // DTO to entity
        User user = UserMapper.INSTANCE.registerDtoRequestToUser(registerDtoRequest);

        // Encode password
        user.setPassword(passwordEncoder.encode(registerDtoRequest.password()));

        // Add role to user
        String roleName = registerDtoRequest.role().name();

        addRoleToUser(user, roleName);

        // Save user in database
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerDtoRequest.username(), registerDtoRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken(authentication);
    }

    @Override
    public void resetPassword(ResetPasswordDtoRequest dtoRequest) {
        // Get user from database
        String username = authenticationFacade.getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username)
        );

        if(!dtoRequest.newPassword().equals(dtoRequest.confirmedNewPassword()))
            throw new AppException(HttpStatus.BAD_REQUEST, "The confirmed password does not match the new password");

        if(passwordEncoder.matches(dtoRequest.newPassword(), user.getPassword()))
            throw new AppException(HttpStatus.BAD_REQUEST, "The new password must be different from the current password");

        // Change password user
        user.setPassword(passwordEncoder.encode(dtoRequest.newPassword()));

        // Save user update in database
        userRepository.save(user);
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
        if(authenticationFacade.getAuthentication() == null)
            return false;
        String username = authenticationFacade.getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username)
        );
        return user.getRole().getName().equals(ROLES.ROLE_ADMIN.name());
    }
}
