package joelsonfatima.dto.response;

import joelsonfatima.entity.DriverLicenseType;
import joelsonfatima.entity.Trip;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Trip} entity
 */
public record TripDtoResponse(Long id, String driverCin, String vehicleLicensePlate, LocalDateTime startDate,
                              LocalDateTime endDate, String startTrip, String endTrip,
                              DriverLicenseType type) implements Serializable {
}