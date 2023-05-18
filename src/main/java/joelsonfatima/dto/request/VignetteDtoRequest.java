package joelsonfatima.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import joelsonfatima.entity.Vignette;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link Vignette} entity
 */
public record VignetteDtoRequest(
        @NotNull(message = "Delivery date is required")
        @FutureOrPresent(message = "Delivery date must be in the present or future")
        LocalDate deliveryDate
) implements Serializable {
}