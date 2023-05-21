package joelsonfatima.dto.response;

import joelsonfatima.entity.Driver;

import java.io.Serializable;

/**
 * A DTO for the {@link Driver} entity
 */
public record DriverUserDtoResponse(
        String cin,
        String firstName,
        String lastName,
        Long userId,
        String username,
        String roleName) implements Serializable {
}