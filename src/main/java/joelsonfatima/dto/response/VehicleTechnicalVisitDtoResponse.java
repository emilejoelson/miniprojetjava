package joelsonfatima.dto.response;

import joelsonfatima.entity.DriverLicenseType;
import joelsonfatima.entity.Vehicle;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link Vehicle} entity
 */
public record VehicleTechnicalVisitDtoResponse(String licensePlate, DriverLicenseType type,
                                               Set<TechnicalVisitDtoResponse> technicalVisits) implements Serializable {
}