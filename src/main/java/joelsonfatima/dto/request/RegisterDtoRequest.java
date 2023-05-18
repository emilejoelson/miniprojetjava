package joelsonfatima.dto.request;

import joelsonfatima.validation.PasswordValid;
import jakarta.validation.constraints.*;

public record RegisterDtoRequest(
        @NotNull(message = "Username is required")
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 5, message = "Username should contain at least {min} characters")
        String username,
        @NotNull(message = "Password is required")
        @NotBlank(message = "Password cannot be blank")
        @PasswordValid
        String password,
        @NotNull(message = "Role is required")
        RoleDtoRequest role) {
}
