package joelsonfatima.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import joelsonfatima.entity.AttestationConformity;

import java.io.Serializable;

/**
 * A DTO for the {@link AttestationConformity} entity
 */
public record AttestationConformityDtoRequest(
        @NotNull(message = "Description should not be null")
        @NotBlank(message = "Description should not be blank")
        @Size(min = 10, max = 200, message = "Description should be between {min} and {max} characters")
        String description
) implements Serializable { }