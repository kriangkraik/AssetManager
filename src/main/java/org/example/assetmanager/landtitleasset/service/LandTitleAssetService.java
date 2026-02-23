package org.example.assetmanager.landtitleasset.service;

import org.example.assetmanager.landtitleasset.entity.LandTitleAssetEntity;

public interface LandTitleAssetService {
    LandTitleAssetEntity depositAsset(LandTitleAssetEntity landTitleAssetEntity);

    LandTitleAssetEntity checkoutLandTitleAsset(String titleDeedNo, String province);

    LandTitleAssetEntity findLandTitleAssetByTitleDeedNo(String titleDeedNo, String province);

    void deleteLandTitleAssetByTitleDeedNo(String titleDeedNo, String province);
}
