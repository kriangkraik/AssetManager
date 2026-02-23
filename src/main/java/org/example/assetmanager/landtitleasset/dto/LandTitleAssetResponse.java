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
        Asset asset = entity != null ? entity.getAsset() : null;
        return LandTitleAssetResponse.builder()
                .id(entity != null ? entity.getAssetId() : null)
                .titleDeedNo(entity != null ? entity.getTitleDeedNo() : null)
                .province(entity != null ? entity.getProvince() : null)
                .assetId(asset != null ? asset.getId() : null)
                .assetName(asset != null ? asset.getName() : null)
                .assetType(asset != null && asset.getType() != null ? asset.getType().name() : null)
                .status(asset != null && asset.getStatus() != null ? asset.getStatus().name() : null)
                .currentUser(asset != null ? asset.getCurrentUser() : null)
                .createdAt(asset != null ? asset.getCreatedAt() : null)
                .updatedAt(asset != null ? asset.getUpdatedAt() : null)
                .build();
    }
}
