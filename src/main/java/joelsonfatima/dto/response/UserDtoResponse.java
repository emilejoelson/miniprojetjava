package joelsonfatima.dto.response;

import joelsonfatima.entity.User;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
public record UserDtoResponse(Long id, String username, RoleDtoResponse role) implements Serializable {
}