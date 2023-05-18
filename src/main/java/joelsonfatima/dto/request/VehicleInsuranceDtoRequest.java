package joelsonfatima.dto.request;

import joelsonfatima.entity.InsuranceDuration;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import joelsonfatima.entity.VehicleInsurance;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link VehicleInsurance} entity
 */
public record VehicleInsuranceDtoRequest(
        @NotNull(message = "Activated date is required")
        @FutureOrPresent(message = "Activated date must be in the present or future")
        LocalDate activatedDate,
        @NotNull(message = "Insurance duration is required")
        InsuranceDuration duration
) implements Serializable {
}