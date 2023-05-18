package joelsonfatima.aop;

import joelsonfatima.auth.IAuthenticationFacade;
import joelsonfatima.dto.ROLES;
import joelsonfatima.entity.Driver;
import joelsonfatima.entity.User;
import joelsonfatima.exception.AppException;
import joelsonfatima.exception.ResourceNotFoundException;
import joelsonfatima.repository.DriverRepository;
import joelsonfatima.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class DriverAuthorizationAspect {
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final IAuthenticationFacade authenticationFacade;

    @Pointcut("execution(* joelsonfatima.controller.DriverController.updateDriver(..))")
    public void updateDriver() {}
    @Pointcut("execution(* joelsonfatima.controller.DriverController.getDriverById(..))")
    public void getDriverById() {}
    @Pointcut("execution(* joelsonfatima.controller.DriverController.getTripsOfDriver(..))")
    public void getTripsOfDriver() {}
    @Pointcut("execution(* joelsonfatima.controller.DriverLicenseController.getLicenseById(..))")
    public void getLicenseById() {}
    @Pointcut("execution(* joelsonfatima.controller.DriverLicenseController.getLicensesForDriver(..))")
    public void getLicensesForDriver() {}
    @Pointcut("execution(* joelsonfatima.controller.VacationController.getVacationById(..))")
    public void getVacationById() {}
    @Pointcut("execution(* joelsonfatima.controller.VacationController.getVacationsForDriver(..))")
    public void getVacationsForDriver() {}

    @Before(value = "(updateDriver() || getDriverById() || getTripsOfDriver() || " +
            "getLicenseById() || getLicensesForDriver() || getVacationById() || " +
            "getVacationsForDriver())  && args(cin)")
    public void checkDriverAuthorization(String cin) {
        Authentication authentication = authenticationFacade.getAuthentication();
        if(authentication == null || !checkAuthorization(cin, authentication)) {
            throw new AppException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }
    private boolean checkAuthorization(String cin, Authentication authentication) {
        String username = authentication.getName();
        if(username == null) {
            return false;
        }

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username)
        );

        String roleName = user.getRole().getName();

        if(roleName.equals(ROLES.ROLE_ADMIN.name()))
            return true;

        Driver driver = driverRepository.findById(cin).orElseThrow(() ->
                new ResourceNotFoundException("Driver", "id", cin)
        );

        return driver.getUser().getUsername().equals(username);
    }
}
