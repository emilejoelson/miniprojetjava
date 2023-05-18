package joelsonfatima.service.impl;

import joelsonfatima.auth.IAuthenticationFacade;
import joelsonfatima.dto.request.DriverDtoRequest;
import joelsonfatima.dto.request.UserDtoRequest;
import joelsonfatima.dto.response.DriverDtoResponse;
import joelsonfatima.dto.response.DriverUserDtoResponse;
import joelsonfatima.dto.response.TripDtoResponse;
import joelsonfatima.exception.AppException;
import joelsonfatima.exception.ResourceNotFoundException;
import joelsonfatima.mapper.DriverMapper;
import joelsonfatima.mapper.TripMapper;
import joelsonfatima.mapper.UserMapper;
import joelsonfatima.repository.DriverRepository;
import joelsonfatima.repository.RoleRepository;
import joelsonfatima.repository.UserRepository;
import joelsonfatima.service.AvailabilityService;
import joelsonfatima.service.DriverService;
import joelsonfatima.entity.Driver;
import joelsonfatima.entity.Role;
import joelsonfatima.entity.Trip;
import joelsonfatima.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final AvailabilityService availabilityService;
    private final UserRepository userRepository;
    private final IAuthenticationFacade authenticationFacade;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public DriverDtoResponse create(DriverDtoRequest driverDto) {
        // Check if driver already exist
        if(driverRepository.existsByCin(driverDto.cin()))
            throw new AppException(HttpStatus.BAD_REQUEST, "Driver already exist");

        // DTO to entity
        Driver driver = DriverMapper.INSTANCE.driverDtoRequestToDriver(driverDto);

        // Save in database
        Driver savedDriver = driverRepository.save(driver);

        return DriverMapper.INSTANCE.driverToDriverDtoResponse(savedDriver);
    }

    @Override
    public DriverDtoResponse getById(String cin) {
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );
//
//        if(!isAdmin() && !isDriverOwnedByLoggedInUser(driver))
//            throw new AppException(HttpStatus.FORBIDDEN, "Access denied");

        return DriverMapper.INSTANCE.driverToDriverDtoResponse(driver);
    }

    @Override
    public List<DriverDtoResponse> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(DriverMapper.INSTANCE::driverToDriverDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DriverDtoResponse> getAvailableDriversBetweenDates(LocalDateTime start, LocalDateTime end) {
        checkIfValidDates(start, end);
        return driverRepository.findAll().stream()
                .filter(d -> availabilityService.isAvailableDriver(d.getCin(), start, end))
                .map(DriverMapper.INSTANCE::driverToDriverDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDtoResponse> getTripsOfDriver(String cin) {
        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // Get trips from driver
        Set<Trip> driverTrips = driver.getTrips();

        if(driverTrips == null)
            return new ArrayList<>();

        return driverTrips.stream()
                .map(TripMapper.INSTANCE::tripToTripDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DriverUserDtoResponse updateUserAccountForDriver(String cin, UserDtoRequest userDtoRequest) {
//        if(!isAdmin())
//            throw new AppException(HttpStatus.FORBIDDEN, "Access denied");

        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // Get role from database
        Role role = roleRepository.findByName(userDtoRequest.role().name()).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", userDtoRequest.role().name())
        );

        // DTO to entity
        User user = UserMapper.INSTANCE.userDtoRequestToUser(userDtoRequest);

        // Add role to user
        user.setRole(role);

        // Encode password user
        user.setPassword(passwordEncoder.encode(userDtoRequest.password()));

        // Save user in database
        User savedUser = userRepository.save(user);

        // Add user to driver
        driver.setUser(savedUser);

        // Save driver update in database
        Driver updatedDriver = driverRepository.save(driver);

        return DriverMapper.INSTANCE.driverToDriverUserDtoResponse(updatedDriver);
    }

    @Override
    public void deleteDriverUserAccount(String cin) {
//        if(!isAdmin())
//            throw new AppException(HttpStatus.FORBIDDEN, "Access denied");

        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // Check if user driver has user
        if(driver.getUser() == null)
            throw new AppException(HttpStatus.NOT_FOUND, String.format("User not found for driver with ID %s", cin));

        // Get user from database
        Long userId = driver.getUser().getId();
        User dbUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId.toString())
        );

        // Delete user from driver
        driver.setUser(null);

        // Save driver update in database
        driverRepository.save(driver);

        // Delete user from database
        userRepository.delete(dbUser);
    }

    @Override
    public DriverDtoResponse update(String cin, DriverDtoRequest driverDto) {
        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // check if permission of update
//        if(!(isAdmin() || isDriverOwnedByLoggedInUser(driver)))
//            throw new AppException(HttpStatus.FORBIDDEN, "Access denied");

        if(StringUtils.hasText(driverDto.cin())) {
            driver.setCin(driverDto.cin());
        }

        if(StringUtils.hasText((driverDto.firstName()))) {
            driver.setFirstName(driverDto.firstName());
        }

        if(StringUtils.hasText(driverDto.lastName())) {
            driver.setLastName(driverDto.lastName());
        }

        if(driverDto.dateOfBirth() != null) {
            driver.setDateOfBirth(driverDto.dateOfBirth());
        }

        // Save driver in database
        Driver updatedDriver = driverRepository.save(driver);

        return DriverMapper.INSTANCE.driverToDriverDtoResponse(updatedDriver);
    }

    @Override
    public void delete(String cin) {
        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );
        // Delete driver from database
        driverRepository.delete(driver);
    }

    private void checkIfValidDates(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Start and end dates are required");
        }
        if(start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now()) || start.isAfter(end))
            throw new AppException(HttpStatus.BAD_REQUEST, "Invalid Dates");
    }

//    private boolean isAdmin() {
//        // Get logged user
//        String username = authenticationFacade.getAuthentication().getName();
//
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new ResourceNotFoundException("User", "id", username)
//        );
//
//        return user.getRole().getName().equals(ROLES.ROLE_ADMIN.name());
//    }

//    private boolean isDriverOwnedByLoggedInUser(Driver driver) {
//        // Get logged user
//        String username = authenticationFacade.getAuthentication().getName();
//        return driver.getUser().getUsername().equals(username);
//    }
}
