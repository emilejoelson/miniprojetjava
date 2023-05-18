package joelsonfatima.service.impl;

import joelsonfatima.dto.request.GrisCardDtoRequest;
import joelsonfatima.dto.response.GrisCardDtoResponse;
import joelsonfatima.entity.GrisCard;
import joelsonfatima.entity.Vehicle;
import joelsonfatima.exception.AppException;
import joelsonfatima.exception.ResourceNotFoundException;
import joelsonfatima.mapper.GrisCardMapper;
import joelsonfatima.repository.GrisCardRepository;
import joelsonfatima.repository.VehicleRepository;
import joelsonfatima.service.GrisCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GrisCardServiceImpl implements GrisCardService {
    private final GrisCardRepository grisCardRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public GrisCardDtoResponse create(String licensePlate, GrisCardDtoRequest cardDtoRequest) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // DTO to entity
        GrisCard grisCard = GrisCardMapper.INSTANCE.grisCardDtoRequestToGrisCard(cardDtoRequest);

        // Add vehicle to gris card
        grisCard.setVehicle(vehicle);

        // Save grid card in database
        GrisCard saved = grisCardRepository.save(grisCard);

        // Add gris card to vehicle
        vehicle.setGrisCard(saved);

        // Save vehicle update in database
        vehicleRepository.save(vehicle);

        return GrisCardMapper.INSTANCE.grisCardToGrisCardDtoResponse(saved);
    }

    @Override
    public GrisCardDtoResponse getById(String licensePlate, Long grisCardId) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get gris card from database
        GrisCard grisCard = grisCardRepository.findById(grisCardId).orElseThrow(
                () -> new ResourceNotFoundException("Gris card", "id", grisCardId.toString())
        );

        // validate gris card for vehicle
        validateGrisCardForVehicle(vehicle, grisCard);

        return GrisCardMapper.INSTANCE.grisCardToGrisCardDtoResponse(grisCard);
    }


    @Override
    public List<GrisCardDtoResponse> getGrisCardsForVehicle(String licensePlate) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get gris card from vehicle
        GrisCard grisCard = vehicle.getGrisCard();

        if(grisCard == null)
            return new ArrayList<>();

        return List.of(GrisCardMapper.INSTANCE.grisCardToGrisCardDtoResponse(grisCard));
    }

    @Override
    public GrisCardDtoResponse update(String licensePlate, Long grisCardId, GrisCardDtoRequest cardDtoRequest) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get gris card from database
        GrisCard grisCard = grisCardRepository.findById(grisCardId).orElseThrow(
                () -> new ResourceNotFoundException("Gris card", "id", grisCardId.toString())
        );

        // validate gris card for vehicle
        validateGrisCardForVehicle(vehicle, grisCard);

        if(cardDtoRequest.activatedDate() != null) {
            grisCard.setActivatedDate(cardDtoRequest.activatedDate());
        }

        // Save gris card update in database
        GrisCard updated = grisCardRepository.save(grisCard);

        return GrisCardMapper.INSTANCE.grisCardToGrisCardDtoResponse(updated);
    }

    @Override
    public void delete(String licensePlate, Long grisCardId) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get gris card from database
        GrisCard grisCard = grisCardRepository.findById(grisCardId).orElseThrow(
                () -> new ResourceNotFoundException("Gris card", "id", grisCardId.toString())
        );

        // validate gris card for vehicle
        validateGrisCardForVehicle(vehicle, grisCard);

        //Delete gris card from database
        grisCardRepository.delete(grisCard);
    }
    private void validateGrisCardForVehicle(Vehicle vehicle, GrisCard grisCard) {
        if(!grisCard.getVehicle().getLicensePlate().equals(vehicle.getLicensePlate()))
            throw new AppException(HttpStatus.NOT_FOUND, String.format("Gris card not found for vehicle with ID %s", vehicle.getLicensePlate()));
    }
}
