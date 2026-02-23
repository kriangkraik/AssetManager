package org.example.assetmanager.asset.services;

import org.example.assetmanager.asset.entities.Asset;
import org.example.assetmanager.asset.enums.AssetStatus;

import java.util.List;

public interface AssetService {
    List<?> getAllAssets();

    Asset depositAsset(Asset asset, Long userId);

    Asset checkoutAsset(Long assetId, Long userId);

    Asset returnAsset(Long assetId, Long userId);

    AssetStatus getAssetStatus(Long assetId);
}
