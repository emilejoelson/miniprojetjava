package joelsonfatima.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import joelsonfatima.entity.TechnicalVisit;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link TechnicalVisit} entity
 */
public record TechnicalVisitDtoRequest(
        @NotNull(message = "Visit date is required")
        @FutureOrPresent
        LocalDate visitDate
) implements Serializable { }