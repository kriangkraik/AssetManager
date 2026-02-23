package org.example.assetmanager.landtitleasset.exeptions;

public class LandTitleAssetNotFoundException extends RuntimeException {
    public LandTitleAssetNotFoundException(String titleDeedNo, String province) {
        super("Land title asset not found. titleDeedNo=" + titleDeedNo + ", province=" + province);
    }
}
