package joelsonfatima.service.impl;

import joelsonfatima.entity.Driver;
import joelsonfatima.entity.DriverLicenseType;
import joelsonfatima.entity.Trip;
import joelsonfatima.entity.Vehicle;
import joelsonfatima.exception.AppException;
import joelsonfatima.exception.ResourceNotFoundException;
import joelsonfatima.repository.DriverRepository;
import joelsonfatima.repository.TripRepository;
import joelsonfatima.repository.VehicleRepository;
import joelsonfatima.service.AffectationService;
import joelsonfatima.service.AvailabilityService;
import joelsonfatima.service.ConformityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AffectationServiceImpl implements AffectationService {
    private final ConformityService conformityService;
    private final AvailabilityService availabilityService;
    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    @Override
    @Transactional
    public Trip DriverToVehicle(String driverId, String vehicleId, Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(
                () -> new ResourceNotFoundException("Trip", "id", tripId.toString())
        );

        checkDriverAvailability(driverId, trip);
        checkVehicleAvailability(vehicleId, trip);
        checkDriverConformity(driverId, trip.getType());
        checkVehicleConformity(vehicleId, trip.getType());

        Driver driver = driverRepository.findById(driverId).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", driverId)
        );
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", vehicleId)
        );

        trip.setDriver(driver);
        trip.setVehicle(vehicle);

        return tripRepository.save(trip);
    }

    private void checkVehicleConformity(String vehicleId, DriverLicenseType type) {
        if(!conformityService.isConformVehicle(vehicleId, type))
            throw new AppException(HttpStatus.BAD_REQUEST, String.format("Vehicle with id %s not conform", vehicleId));
    }

    private void checkDriverConformity(String driverId, DriverLicenseType type) {
        if(!conformityService.hasDriverLicense(driverId, type))
            throw new AppException(HttpStatus.BAD_REQUEST, String.format("Driver with id %s not conform", driverId));
    }

    private void checkVehicleAvailability(String vehicleId, Trip trip) {
        if(!availabilityService.isAvailableVehicle(vehicleId, trip.getStartDate(), trip.getEndDate()))
            throw new AppException(HttpStatus.BAD_REQUEST, String.format("Vehicle with id %s not available", vehicleId));
    }

    private void checkDriverAvailability(String driverId, Trip trip) {
        if(!availabilityService.isAvailableDriver(driverId, trip.getStartDate(), trip.getEndDate()))
            throw new AppException(HttpStatus.BAD_REQUEST, String.format("Driver with id %s not available", driverId));
    }
}
