package joelsonfatima.dto.response;

import joelsonfatima.entity.DriverLicenseType;
import joelsonfatima.entity.Vehicle;

import java.io.Serializable;

/**
 * A DTO for the {@link Vehicle} entity
 */
public record VehicleDtoResponse(String licensePlate, Integer year, DriverLicenseType type) implements Serializable {
}