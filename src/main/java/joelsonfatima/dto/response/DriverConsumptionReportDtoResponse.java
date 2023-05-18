package joelsonfatima.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * A DTO for the {@link DriverConsumptionReport} entity
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DriverConsumptionReportDtoResponse(Long id, String driverCin, double totalFuelConsumed,
                                                 double totalDistanceTravelled,
                                                 double fuelEfficiency) implements Serializable {
}