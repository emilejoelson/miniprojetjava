package joelsonfatima.service;

import joelsonfatima.dto.request.DriverDtoRequest;
import joelsonfatima.dto.request.UserDtoRequest;
import joelsonfatima.dto.response.DriverDtoResponse;
import joelsonfatima.dto.response.DriverUserDtoResponse;
import joelsonfatima.dto.response.TripDtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface DriverService {
    DriverDtoResponse create(DriverDtoRequest driverDto);
    DriverDtoResponse getById(String cin);
    List<DriverDtoResponse> getAllDrivers();
    List<DriverDtoResponse> getAvailableDriversBetweenDates(LocalDateTime start, LocalDateTime end);
    List<TripDtoResponse> getTripsOfDriver(String cin);
    DriverUserDtoResponse updateUserAccountForDriver(String cin, UserDtoRequest userDtoRequest);
    void deleteDriverUserAccount(String cin);
    DriverDtoResponse update(String cin, DriverDtoRequest driverDto);
    void delete(String cin);

}
