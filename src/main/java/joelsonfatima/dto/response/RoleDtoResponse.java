package joelsonfatima.dto.response;

import joelsonfatima.entity.Role;

import java.io.Serializable;

/**
 * A DTO for the {@link Role} entity
 */
public record RoleDtoResponse(Long id, String name) implements Serializable {
}