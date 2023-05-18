package joelsonfatima.dto.response;

import joelsonfatima.entity.GrisCard;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link GrisCard} entity
 */
public record GrisCardDtoResponse(Long id, LocalDate activatedDate) implements Serializable {
}