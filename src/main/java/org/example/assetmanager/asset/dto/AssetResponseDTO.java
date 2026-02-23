package org.example.assetmanager.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.example.assetmanager.asset.entities.Asset;
import org.example.assetmanager.asset.enums.AssetStatus;
import org.example.assetmanager.asset.enums.AssetType;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Asset response")
public class AssetResponseDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Laptop")
    private String name;

    @Schema(example = "Dell XPS 13")
    private String description;

    @Schema(example = "ELECTRONIC")
    private AssetType type;

    @Schema(example = "2")
    private Long currentUserId;

    @Schema(example = "AVAILABLE")
    private AssetStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AssetResponseDTO from(Asset asset) {
        return AssetResponseDTO.builder()
                .id(asset.getId())
                .name(asset.getName())
                .description(asset.getDescription())
                .type(asset.getType())
                .currentUserId(asset.getCurrentUser() == null ? null : asset.getCurrentUser().getId())
                .status(asset.getStatus())
                .createdAt(asset.getCreatedAt())
                .updatedAt(asset.getUpdatedAt())
                .build();
    }
}
