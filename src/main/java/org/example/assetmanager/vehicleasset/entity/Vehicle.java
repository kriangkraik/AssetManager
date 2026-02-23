package org.example.assetmanager.vehicleasset.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.assetmanager.asset.entities.Asset;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Table(name = "vehicles")
public class Vehicle extends Asset {

    @Column(name = "finance_code", nullable = false, length = 10, unique = true)
    private String financeCode;

    @Column(name = "license_plate", nullable = false, length = 50, unique = true)
    private String licensePlate;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "model", nullable = false, length = 50)
    private String model;

    @Column(length = 50)
    private String engineNumber;

    @Column(length = 50)
    private String chassisNumber;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

}
