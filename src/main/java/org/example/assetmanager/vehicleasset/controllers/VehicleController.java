package org.example.assetmanager.vehicleasset.controllers;

import lombok.RequiredArgsConstructor;
import org.example.assetmanager.vehicleasset.dtos.ExpiringVehicleDTO;
import org.example.assetmanager.vehicleasset.dtos.VihecleResponseDTO;
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
    public ResponseEntity<VihecleResponseDTO> getAllVehicle() {
        return ResponseEntity.ok((VihecleResponseDTO) vehicleService.getAllVehicles());
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
