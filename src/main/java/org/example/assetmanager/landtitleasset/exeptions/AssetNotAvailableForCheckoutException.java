package org.example.assetmanager.landtitleasset.exeptions;

import org.example.assetmanager.asset.enums.AssetStatus;

public class AssetNotAvailableForCheckoutException extends RuntimeException {
    public AssetNotAvailableForCheckoutException(Long assetId) {
        super("Asset with id " + assetId + " is not available for checkout");
    }

    public AssetNotAvailableForCheckoutException(Long assetId, AssetStatus status) {
        super("Asset with id " + assetId + " cannot be checked out. Current status: " + status);
    }

}
