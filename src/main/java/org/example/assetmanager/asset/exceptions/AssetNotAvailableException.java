package org.example.assetmanager.asset.exceptions;

public class AssetNotAvailableException extends BusinessException {

    // เบิกไม่ได้
    public AssetNotAvailableException(Long assetId) {
        super(
                "ASSET_NOT_AVAILABLE",
                "Asset is not available for checkout. assetId=" + assetId
        );
    }

}
