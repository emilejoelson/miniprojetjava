package joelsonfatima.dto.response;

import joelsonfatima.entity.Vignette;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link Vignette} entity
 */
public record VignetteDtoResponse(Long id, LocalDate deliveryDate) implements Serializable {
}