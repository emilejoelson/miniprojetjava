package joelsonfatima.service.impl;

import joelsonfatima.dto.request.DriverVehicleToTripDto;
import joelsonfatima.dto.request.TripDtoRequest;
import joelsonfatima.dto.response.TripDtoResponse;
import joelsonfatima.entity.Trip;
import joelsonfatima.exception.ResourceNotFoundException;
import joelsonfatima.mapper.TripMapper;
import joelsonfatima.repository.TripRepository;
import joelsonfatima.service.AffectationService;
import joelsonfatima.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final AffectationService affectationService;

    @Override
    public TripDtoResponse create(TripDtoRequest tripDto) {
        // DTO to entity
        Trip trip = TripMapper.INSTANCE.tripDtoRequestToTrio(tripDto);

        // Save trip in database
        Trip savedTrip = tripRepository.save(trip);

        return TripMapper.INSTANCE.tripToTripDtoResponse(savedTrip);
    }

    @Override
    public TripDtoResponse assignDriverAndVehicleToTrip(Long tripId, DriverVehicleToTripDto dto) {
        // Add driver and vehicle to trip
        Trip trip = affectationService.DriverToVehicle(dto.driverId(), dto.vehicleId(), tripId);

        // Save updated trip in database
        Trip updatedTrip = tripRepository.save(trip);

        return TripMapper.INSTANCE.tripToTripDtoResponse(updatedTrip);
    }

    @Override
    public TripDtoResponse getById(Long tripId) {
        // Get trip from database
        Trip trip = tripRepository.findById(tripId).orElseThrow(
                () -> new ResourceNotFoundException("Trip", "id", tripId.toString())
        );

        return TripMapper.INSTANCE.tripToTripDtoResponse(trip);
    }

    @Override
    public List<TripDtoResponse> getAllTrip() {
        return tripRepository.findAll().stream()
                .map(TripMapper.INSTANCE::tripToTripDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDtoResponse> getAllTripBetweenDates(LocalDateTime start, LocalDateTime end) {
        return tripRepository.findTripBetweenDates(start, end).stream()
                .map(TripMapper.INSTANCE::tripToTripDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TripDtoResponse update(Long tripId, TripDtoRequest tripDto) {
        // Get trip from database
        Trip trip = tripRepository.findById(tripId).orElseThrow(
                () -> new ResourceNotFoundException("Trip", "id", tripId.toString())
        );

        if(tripDto.startDate() != null)
            trip.setStartDate(tripDto.startDate());

        if(tripDto.endDate() != null)
            trip.setEndDate(tripDto.endDate());

        if(StringUtils.hasText(tripDto.startTrip()))
            trip.setStartTrip(tripDto.startTrip());

        if(StringUtils.hasText(tripDto.endTrip()))
            trip.setEndTrip(tripDto.endTrip());

        if(tripDto.type() != null)
            trip.setType(tripDto.type());

        // Save update in database
        Trip updatedTrip = tripRepository.save(trip);

        return TripMapper.INSTANCE.tripToTripDtoResponse(updatedTrip);
    }

    @Override
    public void delete(Long tripId) {
        // Get trip from database
        Trip trip = tripRepository.findById(tripId).orElseThrow(
                () -> new ResourceNotFoundException("Trip", "id", tripId.toString())
        );

        tripRepository.delete(trip);
    }
}
