package joelsonfatima.service.impl;

import joelsonfatima.dto.request.AttestationConformityDtoRequest;
import joelsonfatima.dto.response.AttestationConformityDtoResponse;
import joelsonfatima.entity.AttestationConformity;
import joelsonfatima.entity.TechnicalVisit;
import joelsonfatima.entity.Vehicle;
import joelsonfatima.exception.AppException;
import joelsonfatima.exception.ResourceNotFoundException;
import joelsonfatima.mapper.AttestationConformityMapper;
import joelsonfatima.repository.AttestationConformityRepository;
import joelsonfatima.repository.TechnicalVisitRepository;
import joelsonfatima.repository.VehicleRepository;
import joelsonfatima.service.AttestationConformityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttestationConformityServiceImpl implements AttestationConformityService {
    private final AttestationConformityRepository attestationConformityRepository;
    private final VehicleRepository vehicleRepository;
    private final TechnicalVisitRepository technicalVisitRepository;

    @Override
    public AttestationConformityDtoResponse createAttestationConformity(
            String licensePlate, Long technicalVisitId, AttestationConformityDtoRequest request) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get technical visit from database
        TechnicalVisit technicalVisit = technicalVisitRepository.findById(technicalVisitId).orElseThrow(
                () -> new ResourceNotFoundException("Technical visit", "id", technicalVisitId.toString())
        );

        // validate technical visit for vehicle
        validateTechnicalVisitForVehicle(vehicle, technicalVisit);

        // DTO to entity
        AttestationConformity attestationConformity = AttestationConformityMapper.INSTANCE.toEntity(request);

        // Add technical visit to attestation
        attestationConformity.setTechnicalVisit(technicalVisit);

        // Save attestation in database
        AttestationConformity savedAttestation = attestationConformityRepository.save(attestationConformity);

        // Add attestation to technical visit
        technicalVisit.setAttestation(savedAttestation);

        // Save technical visit update in database
        technicalVisitRepository.save(technicalVisit);

        return mapToAttestationConformityDtoResponse(vehicle, technicalVisit, savedAttestation);
    }

    @Override
    public AttestationConformityDtoResponse getById(String licensePlate, Long technicalVisitId, Long attestationId) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get technical visit from database
        TechnicalVisit technicalVisit = technicalVisitRepository.findById(technicalVisitId).orElseThrow(
                () -> new ResourceNotFoundException("Technical visit", "id", technicalVisitId.toString())
        );

        // validate technical visit for vehicle
        validateTechnicalVisitForVehicle(vehicle, technicalVisit);

        // Get attestation from database
        AttestationConformity attestation = attestationConformityRepository.findById(attestationId).orElseThrow(
                () -> new ResourceNotFoundException("Attestation conformity", "id", attestationId.toString())
        );

        // validate attestation for technical visit
        validateAttestationForTechnicalVisit(technicalVisit, attestation);

        return mapToAttestationConformityDtoResponse(vehicle, technicalVisit, attestation);
    }

    @Override
    public List<AttestationConformityDtoResponse> getAllAttestation(String licensePlate, Long technicalVisitId) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get technical visit from database
        TechnicalVisit technicalVisit = technicalVisitRepository.findById(technicalVisitId).orElseThrow(
                () -> new ResourceNotFoundException("Technical visit", "id", technicalVisitId.toString())
        );

        // validate technical visit for vehicle
        validateTechnicalVisitForVehicle(vehicle, technicalVisit);

        // Get attestation from technical visit
        AttestationConformity attestation = technicalVisit.getAttestation();

        if(attestation == null)
            return new ArrayList<>();

        return List.of(mapToAttestationConformityDtoResponse(vehicle, technicalVisit, attestation));
    }

    @Override
    public AttestationConformityDtoResponse update(
            String licensePlate, Long technicalVisitId, Long attestationId, AttestationConformityDtoRequest request) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get technical visit from database
        TechnicalVisit technicalVisit = technicalVisitRepository.findById(technicalVisitId).orElseThrow(
                () -> new ResourceNotFoundException("Technical visit", "id", technicalVisitId.toString())
        );

        // validate technical visit for vehicle
        validateTechnicalVisitForVehicle(vehicle, technicalVisit);

        // Get attestation from database
        AttestationConformity attestation = attestationConformityRepository.findById(attestationId).orElseThrow(
                () -> new ResourceNotFoundException("Attestation conformity", "id", attestationId.toString())
        );

        // validate attestation for technical visit
        validateAttestationForTechnicalVisit(technicalVisit, attestation);

        if(StringUtils.hasText(request.description())) {
            attestation.setDescription(request.description());
        }

        // Save attestation update in database
        AttestationConformity updatedAttestation = attestationConformityRepository.save(attestation);

        return mapToAttestationConformityDtoResponse(vehicle, technicalVisit, updatedAttestation);
    }

    @Override
    public void delete(String licensePlate, Long technicalVisitId, Long attestationId) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get technical visit from database
        TechnicalVisit technicalVisit = technicalVisitRepository.findById(technicalVisitId).orElseThrow(
                () -> new ResourceNotFoundException("Technical visit", "id", technicalVisitId.toString())
        );

        // validate technical visit for vehicle
        validateTechnicalVisitForVehicle(vehicle, technicalVisit);

        // Get attestation from database
        AttestationConformity attestation = attestationConformityRepository.findById(attestationId).orElseThrow(
                () -> new ResourceNotFoundException("Attestation conformity", "id", attestationId.toString())
        );

        // Validate attestation for technical visit
        validateAttestationForTechnicalVisit(technicalVisit, attestation);

        // Delete attestation from database
        attestationConformityRepository.delete(attestation);
    }

    private void validateTechnicalVisitForVehicle(Vehicle vehicle, TechnicalVisit technicalVisit) {
        if(!technicalVisit.getVehicle_technical_visit().getLicensePlate().equals(vehicle.getLicensePlate()))
            throw new AppException(HttpStatus.NOT_FOUND, String.format("Technical visit not found for vehicle with ID %s", vehicle.getLicensePlate()));
    }

    private void validateAttestationForTechnicalVisit(TechnicalVisit technicalVisit, AttestationConformity attestation) {
        if(!attestation.getTechnicalVisit().getId().equals(technicalVisit.getId()))
            throw new AppException(HttpStatus.NOT_FOUND, String.format("Attestation conformity not found for technical visit with ID %dl", technicalVisit.getId()));
    }

    private AttestationConformityDtoResponse mapToAttestationConformityDtoResponse(Vehicle vehicle, TechnicalVisit technicalVisit, AttestationConformity attestation) {
        return new AttestationConformityDtoResponse(
                vehicle.getLicensePlate(),
                technicalVisit.getId(),
                attestation.getId(),
                attestation.getDescription()
        );
    }
}
