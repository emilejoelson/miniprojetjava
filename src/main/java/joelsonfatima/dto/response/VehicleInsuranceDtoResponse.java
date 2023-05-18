package joelsonfatima.dto.response;

import joelsonfatima.entity.InsuranceDuration;
import joelsonfatima.entity.VehicleInsurance;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link VehicleInsurance} entity
 */
public record VehicleInsuranceDtoResponse(Long id, LocalDate activatedDate,
                                          InsuranceDuration duration) implements Serializable {
}