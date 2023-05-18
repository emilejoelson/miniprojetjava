package joelsonfatima.service;

import joelsonfatima.dto.request.AttestationConformityDtoRequest;
import joelsonfatima.dto.response.AttestationConformityDtoResponse;

import java.util.List;

public interface AttestationConformityService {
    AttestationConformityDtoResponse createAttestationConformity(String licensePlate, Long technicalVisitId, AttestationConformityDtoRequest request);
    AttestationConformityDtoResponse getById(String licensePlate, Long technicalVisitId, Long attestationId);
    List<AttestationConformityDtoResponse> getAllAttestation(String licensePlate, Long technicalVisitId);
    AttestationConformityDtoResponse update(
            String licensePlate, Long technicalVisitId, Long attestationId, AttestationConformityDtoRequest request);
    void delete(String licensePlate, Long technicalVisitId, Long attestationId);
}
