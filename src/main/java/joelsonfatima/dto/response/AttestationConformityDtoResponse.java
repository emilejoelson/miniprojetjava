package joelsonfatima.dto.response;

import joelsonfatima.entity.AttestationConformity;

import java.io.Serializable;

/**
 * A DTO for the {@link AttestationConformity} entity
 */
public record AttestationConformityDtoResponse(
        String vehicleLicensePlate,
        Long technicalVisitId,
        Long attestationId,
        String description) implements Serializable {
}