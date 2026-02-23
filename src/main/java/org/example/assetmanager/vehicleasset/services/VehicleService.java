package org.example.assetmanager.vehicleasset.services;

import lombok.RequiredArgsConstructor;
import org.example.assetmanager.asset.enums.AssetStatus;
import org.example.assetmanager.vehicleasset.exceptions.VehicleNotFoundException;
import org.example.assetmanager.vehicleasset.dtos.ExpiringVehicleDTO;
import org.example.assetmanager.vehicleasset.entity.Vehicle;
import org.example.assetmanager.vehicleasset.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getByLicensePlateAndChassisNumber(String licensePlate, String chassisNumber) {
        return vehicleRepository
                .findByLicensePlateAndChassisNumber(licensePlate, chassisNumber)
                .orElseThrow(() -> new VehicleNotFoundException(
                        "license plate " + licensePlate + " and chassis number " + chassisNumber
                ));
    }

    public Vehicle getVehiclesByLicensePlate(String licensePlate) {
        return vehicleRepository
                .findByLicensePlate(licensePlate)
                .orElseThrow(() -> new VehicleNotFoundException("license plate " + licensePlate));
    }

    public Vehicle getByLicensePlateAndStatus(String licensePlate, AssetStatus status) {
        return vehicleRepository
                .findByLicensePlateAndStatus(licensePlate, status)
                .orElseThrow(() -> new VehicleNotFoundException(
                        "license plate " + licensePlate + " and status " + status
                ));
    }

    public List<Vehicle> getByExpiryDate(LocalDate expiryDate) {
        return vehicleRepository.findByExpiryDate(expiryDate);
    }

    public List<Vehicle> getByExpiryDateBefore(LocalDate date) {
        return vehicleRepository.findByExpiryDateBefore(date);
    }

    public List<Vehicle> getByExpiryDateBetween(LocalDate start, LocalDate end) {
        return vehicleRepository.findByExpiryDateBetween(start, end);
    }

    public List<Vehicle> searchByLicensePlate(String keyword) {
        return vehicleRepository.findByLicensePlateContaining(keyword);
    }

    public List<ExpiringVehicleDTO> getExpiringVehicles(int days) {
        LocalDate targetDate = LocalDate.now().plusDays(days);
        return vehicleRepository.findExpiringVehicles(targetDate);
    }

}
