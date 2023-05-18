package joelsonfatima.dto.response;

import joelsonfatima.entity.DriverLicenseType;
import joelsonfatima.entity.DriverLicense;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link DriverLicense} entity
 */
public record DriverLicenseDtoResponse(Long id,
                                       LocalDateTime deliveryDate,
                                       DriverLicenseType licenseType,
                                       String driverCin) implements Serializable {
}