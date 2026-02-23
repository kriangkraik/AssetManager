package org.example.assetmanager.asset.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.assetmanager.asset.dto.CheckoutRequest;
import org.example.assetmanager.asset.dto.DepositRequest;
import org.example.assetmanager.asset.dto.ReturnRequest;
import org.example.assetmanager.asset.entities.Asset;
import org.example.assetmanager.asset.enums.AssetStatus;
import org.example.assetmanager.asset.services.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;

    @GetMapping
    public ResponseEntity<List<?>> getAllAsset() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    @PostMapping
    public ResponseEntity<Asset> depositAsset(@Valid @RequestBody DepositRequest depositRequest) {
        return ResponseEntity.ok(
                assetService.depositAsset(
                        depositRequest.getAsset(),
                        depositRequest.getUserId())
        );
    }

    @PostMapping("/{assetId}/checkout")
    public ResponseEntity<Asset> checkoutAsset(@PathVariable Long assetId, @Valid @RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(
                assetService.checkoutAsset(
                        assetId,
                        request.getUserId())
        );
    }

    @PostMapping("/{assetId}/return")
    public ResponseEntity<Asset> returnAsset(@PathVariable Long assetId, @Valid @RequestBody ReturnRequest returnRequest) {
        return ResponseEntity.ok(
                assetService.returnAsset(
                        assetId,
                        returnRequest.getUserId())
        );
    }

    @GetMapping("/{assetId}/status")
    public ResponseEntity<AssetStatus> getAssetStatus(@PathVariable Long assetId) {
        return ResponseEntity.ok(assetService.getAssetStatus(assetId));
    }
}
