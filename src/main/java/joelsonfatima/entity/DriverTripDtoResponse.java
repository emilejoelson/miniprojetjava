package joelsonfatima.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * A DTO for the {@link Driver} entity
 */
public record DriverTripDtoResponse(Set<TripDto> trips) implements Serializable {
    /**
     * A DTO for the {@link Trip} entity
     */
    public record TripDto(String vehicleLicensePlate, LocalDateTime startDate, LocalDateTime endDate,
                          String startTrip, String endTrip, DriverLicenseType type) implements Serializable {
    }
}