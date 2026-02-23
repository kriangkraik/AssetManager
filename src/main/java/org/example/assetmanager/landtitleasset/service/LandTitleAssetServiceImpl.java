package org.example.assetmanager.landtitleasset.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.assetmanager.asset.entities.Asset;
import org.example.assetmanager.asset.enums.AssetStatus;
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

        if (landTitleAssetEntity.getTitleDeedNo() == null || landTitleAssetEntity.getTitleDeedNo().isBlank()) {
            throw new InvalidTitleDeedException();
        }

        if (landTitleAssetEntity.getProvince() == null || landTitleAssetEntity.getProvince().isBlank()) {
            throw new InvalidProvinceException();
        }

        LandTitleAssetEntity newAsset = LandTitleAssetEntity.builder()
                .titleDeedNo(landTitleAssetEntity.getTitleDeedNo())
                .province(landTitleAssetEntity.getProvince())
                .build();
        return landTitleAssetRepository.save(newAsset);
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
