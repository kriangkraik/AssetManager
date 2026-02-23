package org.example.assetmanager.vehicleasset.dtos;

import java.time.LocalDate;

public record ExpiringVehicleDTO(String licensePlate, LocalDate expiryDate) {
}
