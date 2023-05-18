package joelsonfatima.service;


import joelsonfatima.dto.request.VehicleInsuranceDtoRequest;
import joelsonfatima.dto.response.VehicleInsuranceDtoResponse;

import java.util.List;

public interface VehicleInsuranceService {
    VehicleInsuranceDtoResponse create(String licensePlate, VehicleInsuranceDtoRequest request);
    VehicleInsuranceDtoResponse getById(String licensePlate, Long insuranceId);
    List<VehicleInsuranceDtoResponse> getInsurancesForVehicle(String licensePlate);
    VehicleInsuranceDtoResponse update(String licensePlate, Long insuranceId, VehicleInsuranceDtoRequest request);
    void delete(String licensePlate, Long insuranceId);
}
