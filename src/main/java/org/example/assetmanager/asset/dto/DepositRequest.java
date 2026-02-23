package org.example.assetmanager.asset.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.assetmanager.asset.entities.Asset;

@Getter
@Setter
public class DepositRequest {
    @NotNull(message = "Asset is required")
    private Asset asset;

    @NotNull(message = "User ID is required")
    @Min(value = 1, message = "User ID must be greater than 0")
    private Long userId;
}
