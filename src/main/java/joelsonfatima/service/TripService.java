package joelsonfatima.service;


import joelsonfatima.dto.request.DriverVehicleToTripDto;
import joelsonfatima.dto.request.TripDtoRequest;
import joelsonfatima.dto.response.TripDtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TripService {
    TripDtoResponse create(TripDtoRequest tripDto);
    TripDtoResponse assignDriverAndVehicleToTrip(Long tripId, DriverVehicleToTripDto dto);
    TripDtoResponse getById(Long tripId);
    List<TripDtoResponse> getAllTrip();
    List<TripDtoResponse> getAllTripBetweenDates(LocalDateTime start, LocalDateTime end);
    TripDtoResponse update(Long tripId, TripDtoRequest tripDto);
    void delete(Long tripId);
}
