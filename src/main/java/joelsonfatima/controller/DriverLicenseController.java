package joelsonfatima.controller;

import joelsonfatima.dto.request.DriverLicenseDtoRequest;
import joelsonfatima.dto.response.DriverLicenseDtoResponse;
import joelsonfatima.service.DriverLicenseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/drivers/{cin}/driverLicenses")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class DriverLicenseController {
    private final DriverLicenseService driverLicenseService;
    @PostMapping
    public ResponseEntity<DriverLicenseDtoResponse> createDriverLicense(
            @PathVariable(name = "cin") String cin,
            @Valid @RequestBody DriverLicenseDtoRequest driverLicenseDto) {
        DriverLicenseDtoResponse response = driverLicenseService.create(cin, driverLicenseDto);
        return ResponseEntity.created(URI.create("/drivers/" + cin + "/driverLicenses/" + response.id()))
                .body(response);
    }
    @GetMapping("/{licenseId}")
    public ResponseEntity<DriverLicenseDtoResponse> getLicenseById(
            @PathVariable(name = "cin") String cin,
            @PathVariable(name = "licenseId") Long licenseId) {
        DriverLicenseDtoResponse response = driverLicenseService.getById(cin, licenseId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<DriverLicenseDtoResponse>> getLicensesForDriver(
            @PathVariable(name = "cin") String cin) {
        List<DriverLicenseDtoResponse> responses = driverLicenseService.getLicensesForDriver(cin);
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{licenseId}")
    public ResponseEntity<DriverLicenseDtoResponse> updateLicense(
            @PathVariable(name = "cin") String cin,
            @PathVariable(name = "licenseId") Long licenseId,
            @Valid @RequestBody DriverLicenseDtoRequest driverLicenseDto) {
        DriverLicenseDtoResponse response = driverLicenseService.update(cin, licenseId, driverLicenseDto);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable(name = "cin") String cin,
            @PathVariable(name = "licenseId") Long licenseId) {
        driverLicenseService.delete(cin, licenseId);
        return ResponseEntity.ok("Driver license deleted successfully");
    }
}
