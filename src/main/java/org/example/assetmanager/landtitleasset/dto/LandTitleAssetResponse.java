package org.example.assetmanager.landtitleasset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.example.assetmanager.asset.entities.Asset;
import org.example.assetmanager.landtitleasset.entity.LandTitleAssetEntity;
import org.example.assetmanager.user.entity.User;

import java.time.LocalDateTime;

@Schema(description = "Land Title Asset response")
@Data
@Builder
public class LandTitleAssetResponse {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "1234/2566")
    private String titleDeedNo;

    @Schema(example = "Bangkok")
    private String province;

    // ----- Asset Info -----
    @Schema(example = "10")
    private Long assetId;

    @Schema(example = "Land Title Document")
    private String assetName;

    @Schema(example = "LAND_TITLE")
    private String assetType;

    @Schema(example = "AVAILABLE")
    private String status;

    @Schema(example = "Kriangkrai Ketkun")
    private User currentUser;

    // ----- Audit -----
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static LandTitleAssetResponse from(LandTitleAssetEntity entity) {
        Asset asset = entity.getAsset();
        return LandTitleAssetResponse.builder()
                .id(entity.getAssetId())
                .titleDeedNo(entity.getTitleDeedNo())
                .province(entity.getProvince())
                .assetId(asset.getId())
                .assetName(asset.getName())
                .assetType(asset.getType().name())
                .status(asset.getStatus().name())
                .currentUser(asset.getCurrentUser())
                .createdAt(asset.getCreatedAt())
                .updatedAt(asset.getUpdatedAt())
                .build();
    }
}
