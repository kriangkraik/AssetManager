package org.example.assetmanager.vehicleasset.repositories;

import org.example.assetmanager.asset.enums.AssetStatus;
import org.example.assetmanager.vehicleasset.dtos.ExpiringVehicleDTO;
import org.example.assetmanager.vehicleasset.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByLicensePlateAndChassisNumber(String licensePlate, String chassisNumber);

    Optional<Vehicle> findByLicensePlate(String licensePlate);

    Optional<Vehicle> findByLicensePlateAndStatus(String licensePlate, AssetStatus status);

    List<Vehicle> findByExpiryDate(LocalDate expiryDate);  // รถหมดอายุวันนี้

    List<Vehicle> findByExpiryDateBefore(LocalDate date);   // รถที่ใกล้หมดอายุ (ก่อนวันกำหนด)

    List<ExpiringVehicleDTO> findByExpiryDateLessThanEqual(int days);    // รถที่หมดอายุแล้ว

    List<Vehicle> findByExpiryDateBetween(LocalDate start, LocalDate end);  // รถที่หมดอายุในช่วงเวลา

    List<Vehicle> findByBrand(String brand);

    List<Vehicle> findByModel(String model);

    List<Vehicle> findByStatus(AssetStatus status);

    List<Vehicle> findByBrandAndModel(String brand, String model);

    List<Vehicle> findByLicensePlateContaining(String licensePlate);

    @Query("""
                SELECT new org.example.assetmanager.vehicleasset.dto.ExpiringVehicleDTO(
                    v.licensePlate,
                    v.expiryDate
                )
                FROM Vehicle v
                WHERE v.expiryDate <= :date
            """)
    List<ExpiringVehicleDTO> findExpiringVehicles(@Param("date") LocalDate date);

    List<ExpiringVehicleDTO> findByExpiryDateLessThanEqual(LocalDate date);

}
