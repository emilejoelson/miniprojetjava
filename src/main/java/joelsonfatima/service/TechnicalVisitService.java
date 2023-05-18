package joelsonfatima.service;


import joelsonfatima.dto.request.TechnicalVisitDtoRequest;
import joelsonfatima.dto.response.TechnicalVisitDtoResponse;

import java.util.List;

public interface TechnicalVisitService {
    TechnicalVisitDtoResponse create(String licensePlate, TechnicalVisitDtoRequest request);
    TechnicalVisitDtoResponse getById(String licensePlate, Long technicalVisitId);
    List<TechnicalVisitDtoResponse> getTechnicalVisitsForVehicle(String licensePlate);
    TechnicalVisitDtoResponse update(String licensePlate, Long technicalVisitId, TechnicalVisitDtoRequest request);
    void delete(String licensePlate, Long technicalVisitId);

}
