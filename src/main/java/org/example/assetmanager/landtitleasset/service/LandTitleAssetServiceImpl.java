package org.example.assetmanager.landtitleasset.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.assetmanager.asset.entities.Asset;
import org.example.assetmanager.asset.enums.AssetStatus;
import org.example.assetmanager.asset.enums.AssetType;
import org.example.assetmanager.landtitleasset.entity.LandTitleAssetEntity;
import org.example.assetmanager.landtitleasset.exeptions.AssetNotAvailableForCheckoutException;
import org.example.assetmanager.landtitleasset.exeptions.InvalidProvinceException;
import org.example.assetmanager.landtitleasset.exeptions.InvalidTitleDeedException;
import org.example.assetmanager.landtitleasset.exeptions.LandTitleAssetNotFoundException;
import org.example.assetmanager.landtitleasset.repository.LandTitleAssetRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LandTitleAssetServiceImpl implements LandTitleAssetService {

    private final LandTitleAssetRepository landTitleAssetRepository;

    // 1. นำฝากทรัพย์สิน
    public LandTitleAssetEntity depositAsset(LandTitleAssetEntity landTitleAssetEntity) {
        if (landTitleAssetEntity == null) {
            throw new IllegalArgumentException("Land title asset payload cannot be null");
        }

        if (landTitleAssetEntity.getTitleDeedNo() == null || landTitleAssetEntity.getTitleDeedNo().isBlank()) {
            throw new InvalidTitleDeedException();
        }

        if (landTitleAssetEntity.getProvince() == null || landTitleAssetEntity.getProvince().isBlank()) {
            throw new InvalidProvinceException();
        }

        if (landTitleAssetEntity.getAddress() == null || landTitleAssetEntity.getAddress().isBlank()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }

        if (landTitleAssetEntity.getCodefinance() == null || landTitleAssetEntity.getCodefinance().isBlank()) {
            throw new IllegalArgumentException("Codefinance cannot be empty");
        }

        Asset requestAsset = landTitleAssetEntity.getAsset();
        if (requestAsset == null) {
            throw new IllegalArgumentException("Asset is required");
        }

        Asset assetToPersist = Asset.builder()
                .name(defaultAssetName(requestAsset.getName(), landTitleAssetEntity.getTitleDeedNo()))
                .type(AssetType.LAND_TITLE)
                .status(AssetStatus.AVAILABLE)
                .currentUser(requestAsset.getCurrentUser())
                .build();

        LandTitleAssetEntity newAsset = LandTitleAssetEntity.builder()
                .asset(assetToPersist)
                .titleDeedNo(landTitleAssetEntity.getTitleDeedNo().trim())
                .province(landTitleAssetEntity.getProvince().trim())
                .address(landTitleAssetEntity.getAddress().trim())
                .codefinance(landTitleAssetEntity.getCodefinance().trim())
                .build();
        return landTitleAssetRepository.save(newAsset);
    }

    private String defaultAssetName(String name, String titleDeedNo) {
        if (name != null && !name.isBlank()) {
            return name.trim();
        }
        return "Land title " + titleDeedNo.trim();
    }

    // 2. เบิกทรัพย์สินไปใช้
    public LandTitleAssetEntity checkoutLandTitleAsset(String titleDeedNo, String province) {
        validateTitleDeed(titleDeedNo, province);
        LandTitleAssetEntity landTitleAsset = findLandTitleAssetByTitleDeedNo(titleDeedNo, province);
        Asset asset = landTitleAsset.getAsset();

        if (landTitleAsset.getAsset().getStatus() == AssetStatus.AVAILABLE) {
            throw new AssetNotAvailableForCheckoutException(landTitleAsset.getAsset().getId(),
                    landTitleAsset.getAsset().getStatus());
        }

        asset.setStatus(AssetStatus.WITHDRAWN);
        return landTitleAsset;
    }

    public LandTitleAssetEntity findLandTitleAssetByTitleDeedNo(String titleDeedNo, String province) {
        validateTitleDeed(titleDeedNo, province);
        return landTitleAssetRepository
                .findByTitleDeedNoAndProvince(titleDeedNo, province)
                .orElseThrow(() -> new LandTitleAssetNotFoundException(titleDeedNo, province));
    }

    private void validateTitleDeed(String titleDeedNo, String province) {
        if (titleDeedNo == null || titleDeedNo.isBlank()) {
            throw new InvalidTitleDeedException();
        }
        if (province == null || province.isBlank()) {
            throw new InvalidProvinceException();
        }
    }

    public void deleteLandTitleAssetByTitleDeedNo(String titleDeedNo, String province) {
        LandTitleAssetEntity entity = landTitleAssetRepository.findByTitleDeedNoAndProvince(titleDeedNo, province)
                .orElseThrow(() -> new LandTitleAssetNotFoundException(titleDeedNo, province));
        landTitleAssetRepository.delete(entity);
    }


}
