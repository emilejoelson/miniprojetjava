package joelsonfatima.dto.response;

import joelsonfatima.entity.DriverLicenseType;
import joelsonfatima.entity.Vehicle;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link Vehicle} entity
 */
public record VehicleAllFieldsDtoResponse(String licensePlate, Integer year, DriverLicenseType type,
                                          GrisCardDtoResponse grisCard, VehicleInsuranceDtoResponse vehicleInsurance,
                                          VignetteDtoResponse vignette,
                                          Set<TechnicalVisitDtoResponse> technicalVisits) implements Serializable {
}