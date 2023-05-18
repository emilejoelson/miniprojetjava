package joelsonfatima.dto.response;

import joelsonfatima.entity.DriverLicenseType;
import joelsonfatima.entity.Vehicle;

import java.io.Serializable;

/**
 * A DTO for the {@link Vehicle} entity
 */
public record VehicleVignetteDtoResponse(String licensePlate, DriverLicenseType type,
                                         VignetteDtoResponse vignette) implements Serializable {
}