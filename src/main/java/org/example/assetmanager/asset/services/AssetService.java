package org.example.assetmanager.asset.services;

import org.example.assetmanager.asset.entities.Asset;

import java.util.List;

public interface AssetService {
    List<?> getAllAssets();

    Asset depositAsset(Asset asset, Long userId);

    Asset checkoutAsset(Long assetId, Long userId);

    Asset returnAsset(Long assetId, Long userId);
}
