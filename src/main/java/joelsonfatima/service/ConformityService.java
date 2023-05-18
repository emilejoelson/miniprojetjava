package joelsonfatima.service;

import joelsonfatima.entity.DriverLicenseType;


public interface ConformityService {
    boolean hasDriverLicense(String driverId, DriverLicenseType licenseType);
    boolean isConformVehicle(String vehicleId, DriverLicenseType licenseType);
}
