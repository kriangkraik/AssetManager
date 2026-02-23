package org.example.assetmanager.asset.exceptions;

public class AssetAlreadyInUseException extends BusinessException {

    public AssetAlreadyInUseException(Long assetId, String currentUser) {
        super(
                "ASSET_ALREADY_IN_USE",
                "Asset is already in use by " + currentUser + ". assetId=" + assetId
        );
    }

}
