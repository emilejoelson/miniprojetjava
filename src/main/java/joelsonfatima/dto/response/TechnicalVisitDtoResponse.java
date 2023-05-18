package joelsonfatima.dto.response;

import joelsonfatima.entity.TechnicalVisit;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link TechnicalVisit} entity
 */
public record TechnicalVisitDtoResponse(
        Long id,
        String vehicleLicensePlate,
        LocalDate visitDate,
        Long attestationId,
        String description) implements Serializable {
}