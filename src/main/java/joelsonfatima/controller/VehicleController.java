package joelsonfatima.controller;

import joelsonfatima.dto.request.VehicleDtoRequest;
import joelsonfatima.service.VehicleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import joelsonfatima.dto.response.VehicleAllFieldsDtoResponse;
import joelsonfatima.dto.response.VehicleDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class VehicleController {
    private final VehicleService vehicleService;
    @PostMapping
    public ResponseEntity<VehicleDtoResponse> createVehicle(@Valid @RequestBody VehicleDtoRequest vehicleDto) {
        VehicleDtoResponse response = vehicleService.create(vehicleDto);
        return ResponseEntity.created(URI.create("/vehicles/" + response.licensePlate()))
                .body(response);
    }
    @GetMapping("/{licensePlate}")
    public ResponseEntity<VehicleAllFieldsDtoResponse> getVehicleById(@PathVariable(name = "licensePlate") String licensePlate) {
        VehicleAllFieldsDtoResponse response = vehicleService.getById(licensePlate);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<VehicleDtoResponse>> getAllVehicles() {
        List<VehicleDtoResponse> responses = vehicleService.getAllVehicle();
        return ResponseEntity.ok(responses);
    }
    @GetMapping(params = {"startDate", "endDate"})
    public ResponseEntity<List<VehicleDtoResponse>> getAvailableVehiclesBetweenDates(
            @RequestParam(name = "startDate") LocalDateTime start,
            @RequestParam(name = "endDate") LocalDateTime end) {
        List<VehicleDtoResponse> responses = vehicleService.getAvailableVehiclesBetweenDates(start, end);
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{licensePlate}")
    public ResponseEntity<VehicleDtoResponse> updateVehicle(
            @PathVariable(name = "licensePlate") String licensePlate,
            @Valid @RequestBody VehicleDtoRequest vehicleDto) {
        VehicleDtoResponse response = vehicleService.update(licensePlate, vehicleDto);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<String> deleteVehicle(@PathVariable(name = "licensePlate") String licensePlate) {
        vehicleService.delete(licensePlate);
        return ResponseEntity.ok("Vehicle deleted successfully");
    }
}
