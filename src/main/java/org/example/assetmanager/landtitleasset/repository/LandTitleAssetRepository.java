package org.example.assetmanager.landtitleasset.repository;

import org.example.assetmanager.landtitleasset.entity.LandTitleAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandTitleAssetRepository extends JpaRepository<LandTitleAssetEntity, Long> {
    Optional<LandTitleAssetEntity> findByTitleDeedNoAndProvince(String titleDeedNo, String province);
}
