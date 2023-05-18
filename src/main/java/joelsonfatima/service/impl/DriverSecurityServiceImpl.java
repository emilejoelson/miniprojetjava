package joelsonfatima.service.impl;

import joelsonfatima.auth.IAuthenticationFacade;
import joelsonfatima.entity.Driver;
import joelsonfatima.repository.DriverRepository;
import joelsonfatima.service.DriverSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverSecurityServiceImpl implements DriverSecurityService {
    private final DriverRepository driverRepository;
    private final IAuthenticationFacade authenticationFacade;
    @Override
    public boolean isDriver(String cin) {
        String username = authenticationFacade.getAuthentication().getName();

        // Get driver from database
        Optional<Driver> optionalDriver = driverRepository.findById(cin);

        if(optionalDriver.isEmpty())
            return false;
        Driver driver = optionalDriver.get();

        return driver.getUser() != null && driver.getUser().getUsername().equals(username);
    }
}
