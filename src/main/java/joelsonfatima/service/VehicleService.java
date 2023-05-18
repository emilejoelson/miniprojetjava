package joelsonfatima.service;

import joelsonfatima.dto.request.VehicleDtoRequest;
import joelsonfatima.dto.response.VehicleAllFieldsDtoResponse;
import joelsonfatima.dto.response.VehicleDtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleService {
    VehicleDtoResponse create(VehicleDtoRequest vehicleDto);
    VehicleAllFieldsDtoResponse getById(String licensePlate);
    List<VehicleDtoResponse> getAllVehicle();
    List<VehicleDtoResponse> getAvailableVehiclesBetweenDates(LocalDateTime start, LocalDateTime end);
    VehicleDtoResponse update(String licensePlate, VehicleDtoRequest vehicleDto);
    void delete(String licensePlate);
}
