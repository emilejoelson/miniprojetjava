package joelsonfatima.dto.request;

import joelsonfatima.entity.DriverLicenseType;
import joelsonfatima.validation.DeliveryDateValid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import joelsonfatima.entity.DriverLicense;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link DriverLicense} entity
 */
public record DriverLicenseDtoRequest(
        @NotNull(message = "Delivery date is required")
        @Past(message = "Delivery date must be in the past")
        @DeliveryDateValid(message = "Delivery date must not be more than 10 years ago")
        LocalDateTime deliveryDate,
        @NotNull(message = "License type is required")
        DriverLicenseType licenseType
) implements Serializable { }