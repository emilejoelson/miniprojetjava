package joelsonfatima.service;

import joelsonfatima.dto.request.VignetteDtoRequest;
import joelsonfatima.dto.response.VignetteDtoResponse;

import java.util.List;

public interface VignetteService {
    VignetteDtoResponse create(String licensePlate, VignetteDtoRequest request);
    VignetteDtoResponse getById(String licensePlate, Long vignetteId);
    List<VignetteDtoResponse> getVignettesForVehicle(String licensePlate);
    VignetteDtoResponse update(String licensePlate, Long vignetteId, VignetteDtoRequest request);
    void delete(String licensePlate, Long vignetteId);

}
