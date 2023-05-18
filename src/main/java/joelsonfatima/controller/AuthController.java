package joelsonfatima.controller;

import joelsonfatima.auth.UserModel;
import joelsonfatima.dto.TokenType;
import joelsonfatima.dto.request.LoginRequestDto;
import joelsonfatima.dto.request.ResetPasswordDtoRequest;
import joelsonfatima.dto.response.JwtAuthResponse;
import joelsonfatima.dto.response.UserDtoResponse;
import joelsonfatima.service.AuthService;
import joelsonfatima.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentification")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    // build login
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginHandler(
            @Valid @RequestBody LoginRequestDto loginDto) {
        String token = authService.login(loginDto);

        JwtAuthResponse response = new JwtAuthResponse(token, TokenType.Bearer);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordDtoRequest dtoRequest) {
        authService.resetPassword(dtoRequest);
        return ResponseEntity.ok("Password updated successfully");
    }

    // get info authenticated user
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public ResponseEntity<UserDtoResponse> getCurrentUser(
            @AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(userService.getUserByUsername(userModel.getUsername()));
    }
}
