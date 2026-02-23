package org.example.assetmanager.asset.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnRequest {
    @NotNull(message = "Asset ID is required")
    @Min(value = 1, message = "Asset ID must be greater than 0")
    private Long assetId;

    @NotNull(message = "User ID is required")
    @Min(value = 1, message = "User ID must be greater than 0")
    private Long userId;
}
