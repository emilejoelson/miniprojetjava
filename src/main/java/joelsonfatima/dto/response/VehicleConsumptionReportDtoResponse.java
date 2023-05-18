package joelsonfatima.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import joelsonfatima.entity.DriverLicenseType;

import java.io.Serializable;

/**
 * A DTO for the {@link VehicleConsumptionReport} entity
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VehicleConsumptionReportDtoResponse(Long id, String vehicleLicensePlate, DriverLicenseType vehicleType,
                                                  double totalFuelConsumed, double totalDistanceTravelled,
                                                  double fuelEfficiency) implements Serializable {
}