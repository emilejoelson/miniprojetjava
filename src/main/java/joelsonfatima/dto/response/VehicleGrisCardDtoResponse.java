package joelsonfatima.dto.response;

import joelsonfatima.entity.DriverLicenseType;
import joelsonfatima.entity.Vehicle;

import java.io.Serializable;

/**
 * A DTO for the {@link Vehicle} entity
 */
public record VehicleGrisCardDtoResponse(String licensePlate, DriverLicenseType type,
                                         GrisCardDtoResponse grisCard) implements Serializable {
}