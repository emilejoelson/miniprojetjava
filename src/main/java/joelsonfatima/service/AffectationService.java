package joelsonfatima.service;

import joelsonfatima.entity.Trip;

public interface AffectationService {
    Trip DriverToVehicle(String driverId, String vehicleId, Long tripId);
}
