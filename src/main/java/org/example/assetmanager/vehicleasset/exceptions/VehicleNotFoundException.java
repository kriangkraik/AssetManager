package org.example.assetmanager.vehicleasset.exceptions;

import org.example.assetmanager.asset.exceptions.BusinessException;

public class VehicleNotFoundException extends BusinessException {

    public VehicleNotFoundException(String criteria) {
        super("VEHICLE_NOT_FOUND", "Vehicle not found for " + criteria);
    }
}
