package joelsonfatima.service.impl;

import joelsonfatima.entity.Driver;
import joelsonfatima.entity.Vacation;
import joelsonfatima.entity.Vehicle;
import joelsonfatima.exception.AppException;
import joelsonfatima.exception.ErrorConstants;
import joelsonfatima.repository.DriverRepository;
import joelsonfatima.repository.VacationRepository;
import joelsonfatima.repository.VehicleRepository;
import joelsonfatima.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {
    private final VacationRepository vacationRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;



    @Override
    public boolean isAvailableDriver(String driverId, LocalDateTime start, LocalDateTime end) {
        validateDate(start, end);

        List<Driver> availableDrivers = driverRepository.findDriversWithoutTripsBetweenDates(start, end);
        Vacation currentVacation = vacationRepository.findFirstByDriverOrderByDescId(driverId).orElse(null);

        boolean notInVacation = checkVacationNotInTrip(currentVacation, start, end);
        boolean notInTrip = checkDriverNotInTrip(driverId, availableDrivers);

        return notInVacation && notInTrip;
    }

    @Override
    public boolean isAvailableVehicle(String vehicleId, LocalDateTime start, LocalDateTime end) {
        validateDate(start, end);

        List<Vehicle> availableVehicles = vehicleRepository.findVehiclesWithoutTripsBetweenDates(start, end);

        boolean notInTrip = checkVehicleNotInTrip(vehicleId, availableVehicles);

        return notInTrip;
    }

    private boolean checkVacationNotInTrip(Vacation currentVacation, LocalDateTime start, LocalDateTime end) {
        if(currentVacation == null)
            return true;
        return currentVacation.getEnd().isBefore(start) || currentVacation.getStart().isAfter(end);
    }

    private boolean checkDriverNotInTrip(String driverId, List<Driver> availableDrivers) {
        return availableDrivers.stream().map(Driver::getCin).anyMatch(d -> d.equals(driverId));
    }

    private boolean checkVehicleNotInTrip(String vehicleId, List<Vehicle> availableVehicles) {
        return availableVehicles.stream().map(Vehicle::getLicensePlate).anyMatch(vehicleId::equals);
    }

    private void validateDate(LocalDateTime start, LocalDateTime end) {
        if(start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now()) || start.isAfter(end)) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorConstants.INVALID_DATE);
        }
    }
}
