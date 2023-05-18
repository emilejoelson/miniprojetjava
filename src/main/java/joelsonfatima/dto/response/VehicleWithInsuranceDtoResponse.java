package joelsonfatima.dto.response;

import joelsonfatima.entity.DriverLicenseType;
import joelsonfatima.entity.Vehicle;

import java.io.Serializable;

/**
 * A DTO for the {@link Vehicle} entity
 */
public record VehicleWithInsuranceDtoResponse(String licensePlate, DriverLicenseType type,
                                              VehicleInsuranceDtoResponse vehicleInsurance) implements Serializable {
}