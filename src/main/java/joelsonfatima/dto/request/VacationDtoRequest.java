package joelsonfatima.dto.request;

import joelsonfatima.validation.StartBeforeEnd;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import joelsonfatima.entity.Vacation;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Vacation} entity
 */
@StartBeforeEnd
public record VacationDtoRequest(
        @NotNull(message = "Start date cannot be null")
        @FutureOrPresent(message = "Start date must be in the future or present")
        LocalDateTime start,
        @NotNull(message = "End date cannot be null")
        @Future(message = "End date must be in the future")
        LocalDateTime end
) implements Serializable {
}