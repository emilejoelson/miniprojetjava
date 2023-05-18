package joelsonfatima.dto.response;

import joelsonfatima.entity.DriverLicenseType;

import java.io.Serializable;
import java.time.LocalDateTime;


public record FuelConsumptionRecordDtoResponse(Long id, Long tripId, LocalDateTime tripStartDate,
                                               LocalDateTime tripEndDate, String tripStartTrip, String tripEndTrip,
                                               DriverLicenseType tripType, String vehicleLicensePlate, String driverCin,

                                               String refuelingLocation) implements Serializable {
}