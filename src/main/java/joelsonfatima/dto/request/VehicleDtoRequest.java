package joelsonfatima.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import joelsonfatima.entity.DriverLicenseType;
import jakarta.validation.constraints.*;
import joelsonfatima.entity.Vehicle;

import java.io.Serializable;
import java.time.Year;

/**
 * A DTO for the {@link Vehicle} entity
 */
public record VehicleDtoRequest(
        @NotNull(message = "License plate is required")
        @NotBlank(message = "License plate cannot be blank")
        String licensePlate,
        @NotNull(message = "Year is required")
        @Min(value = 2010, message = "Year must be greater than or equal to {value}")
        Integer year,
        @NotNull(message = "Driver license type is required")
        DriverLicenseType type
) implements Serializable {
    private static final int CURRENT_YEAR = Year.now().getValue();
    @AssertTrue(message = "Year must be less than or equal to the current year")
    @JsonIgnore
    private boolean isValidYear() {
        return year <= CURRENT_YEAR;
    }
}