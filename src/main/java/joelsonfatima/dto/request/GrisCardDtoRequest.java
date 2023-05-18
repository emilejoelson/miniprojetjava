package joelsonfatima.dto.request;

import joelsonfatima.validation.ActivatedDateValid;
import jakarta.validation.constraints.NotNull;
import joelsonfatima.entity.GrisCard;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link GrisCard} entity
 */
public record GrisCardDtoRequest(
        @NotNull(message = "Activated date is required")
        @ActivatedDateValid(message = "Activated date must not be more than 10 years ago")
        LocalDate activatedDate
) implements Serializable { }