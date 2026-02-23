package org.example.assetmanager.vehicleasset.controllers;

import lombok.RequiredArgsConstructor;
import org.example.assetmanager.asset.enums.AssetStatus;
import org.example.assetmanager.vehicleasset.dtos.ExpiringVehicleDTO;
import org.example.assetmanager.vehicleasset.entity.Vehicle;
import org.example.assetmanager.vehicleasset.services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping("/")
    public ResponseEntity<List<Vehicle>> getAllVehicle() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/search")
    public ResponseEntity<Vehicle> getVehicleByLicensePlateAndChassis(
            @RequestParam String licensePlate,
            @RequestParam String chassisNumber) {
        return ResponseEntity.ok(vehicleService.getByLicensePlateAndChassisNumber(licensePlate, chassisNumber));
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<Vehicle> getVehicleByLicensePlate(@PathVariable String licensePlate) {
        return ResponseEntity.ok(vehicleService.getVehiclesByLicensePlate(licensePlate));
    }

    @GetMapping("/{licensePlate}/status")
    public ResponseEntity<Vehicle> getVehicleByLicensePlateAndStatus(
            @PathVariable String licensePlate,
            @RequestParam AssetStatus status) {
        return ResponseEntity.ok(vehicleService.getByLicensePlateAndStatus(licensePlate, status));
    }

    @PostMapping("/vehicle")
    public String addVehicle() {
        return "vehicle";
    }

    @PutMapping("/vehicle")
    public String updateVehicle() {
        return "vehicle";
    }

    @DeleteMapping("/vehicle")
    public String deleteVehicle() {
        return "vehicle";
    }


    @GetMapping("/vehicles/expiring")
    public ResponseEntity<List<ExpiringVehicleDTO>> getExpiring(
            @RequestParam(defaultValue = "30") int days) {

        return ResponseEntity.ok(
                vehicleService.getExpiringVehicles(days)
        );
    }
}
