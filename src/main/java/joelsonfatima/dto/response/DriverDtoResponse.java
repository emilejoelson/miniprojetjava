package joelsonfatima.dto.response;

import joelsonfatima.entity.Driver;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link Driver} entity
 */
public record DriverDtoResponse(String cin, String firstName, String lastName, LocalDate dateOfBirth) implements Serializable {
}