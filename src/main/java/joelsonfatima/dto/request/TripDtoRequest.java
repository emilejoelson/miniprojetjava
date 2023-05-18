package joelsonfatima.dto.request;

import joelsonfatima.entity.DriverLicenseType;
import joelsonfatima.validation.StartBeforeEnd;
import jakarta.validation.constraints.*;
import joelsonfatima.entity.Trip;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Trip} entity
 */
@StartBeforeEnd
public record TripDtoRequest(
        @NotNull(message = "Start date cannot be null")
        @FutureOrPresent(message = "Start date must be in the future or present")
        LocalDateTime startDate,
        @NotNull(message = "End date cannot be null")
        @Future(message = "End date must be in the future")
        LocalDateTime endDate,
        @NotNull(message = "Start trip is required")
        @NotBlank(message = "Start trip cannot be blank")
        String startTrip,
        @NotNull(message = "End trip is required")
        @NotBlank(message = "End trip cannot be blank")
        String endTrip,
        @NotNull(message = "License type cannot be null")
        DriverLicenseType type
) implements Serializable {
}