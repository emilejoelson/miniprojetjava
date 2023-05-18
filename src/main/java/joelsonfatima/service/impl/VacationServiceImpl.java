package joelsonfatima.service.impl;

import joelsonfatima.dto.request.VacationDtoRequest;
import joelsonfatima.dto.response.VacationDtoResponse;
import joelsonfatima.entity.Driver;
import joelsonfatima.entity.Vacation;
import joelsonfatima.exception.AppException;
import joelsonfatima.exception.ResourceNotFoundException;
import joelsonfatima.mapper.VacationMapper;
import joelsonfatima.repository.DriverRepository;
import joelsonfatima.repository.VacationRepository;
import joelsonfatima.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {
    private final VacationRepository vacationRepository;
    private final DriverRepository driverRepository;
    @Override
    public VacationDtoResponse create(String cin, VacationDtoRequest vacationDto) {
        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // DTO to entity
        Vacation vacation = VacationMapper.INSTANCE.vacationDtoRequestToVacation(vacationDto);

        // Add vacation to driver
        vacation.setDriver(driver);

        // Save vacation in database
        Vacation savedVacation = vacationRepository.save(vacation);

        return VacationMapper.INSTANCE.vacationToVacationDtoResponse(savedVacation);
    }

    @Override
    public VacationDtoResponse getById(String cin, Long vacationId) {
        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // Get vacation from database
        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow(
                () -> new ResourceNotFoundException("Vacation", "id", vacationId.toString())
        );

        // Validate vacation for driver
        validateVacationForDriver(driver, vacation);

        return VacationMapper.INSTANCE.vacationToVacationDtoResponse(vacation);
    }



    @Override
    public List<VacationDtoResponse> getVacationsForDriver(String cin) {
        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        return driver.getVacations().stream()
                .map(VacationMapper.INSTANCE::vacationToVacationDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VacationDtoResponse update(String cin, Long vacationId, VacationDtoRequest vacationDto) {
        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // Get vacation from database
        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow(
                () -> new ResourceNotFoundException("Vacation", "id", vacationId.toString())
        );

        /// Validate vacation for driver
        validateVacationForDriver(driver, vacation);

        if(vacationDto.start() != null)
            vacation.setStart(vacationDto.start());

        if(vacationDto.end() != null)
            vacation.setEnd(vacationDto.end());

        // Save update vacation into database
        Vacation updatedVacation = vacationRepository.save(vacation);

        return VacationMapper.INSTANCE.vacationToVacationDtoResponse(updatedVacation);
    }

    @Override
    public void delete(String cin, Long vacationId) {
        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // Get vacation from database
        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow(
                () -> new ResourceNotFoundException("Vacation", "id", vacationId.toString())
        );

        /// Validate vacation for driver
        validateVacationForDriver(driver, vacation);

        vacationRepository.delete(vacation);
    }

    private void validateVacationForDriver(Driver driver, Vacation vacation) {
        if(!vacation.getDriver().getCin().equals(driver.getCin())) {
            throw new AppException(HttpStatus.NOT_FOUND, String.format("Vacation not found for driver with ID %s", driver.getCin()));
        }
    }
}
