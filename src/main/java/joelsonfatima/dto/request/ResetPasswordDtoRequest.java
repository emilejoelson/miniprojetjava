package joelsonfatima.dto.request;

import joelsonfatima.validation.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


public record ResetPasswordDtoRequest(
        @NotEmpty(message = "Password is required")
        @NotBlank(message = "Password should not be blank")
        String oldPassword,
        @NotEmpty(message = "Password is required")
        @NotBlank(message = "Password should not be blank")
        @PasswordValid
        String newPassword,
        @NotEmpty(message = "Password is required")
        @NotBlank(message = "Password should not be blank")
        @PasswordValid
        String confirmedNewPassword
) {
}
