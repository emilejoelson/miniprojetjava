package joelsonfatima.dto.response;

import joelsonfatima.entity.Vacation;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Vacation} entity
 */
public record VacationDtoResponse(Long id, LocalDateTime start, LocalDateTime end,
                                  String driverCin) implements Serializable {
}