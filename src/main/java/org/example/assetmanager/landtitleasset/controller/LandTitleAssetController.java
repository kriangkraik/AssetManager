package org.example.assetmanager.landtitleasset.controller;

import lombok.RequiredArgsConstructor;
import org.example.assetmanager.landtitleasset.api.StandardResponse;
import org.example.assetmanager.landtitleasset.dto.LandTitleAssetResponse;
import org.example.assetmanager.landtitleasset.entity.LandTitleAssetEntity;
import org.example.assetmanager.landtitleasset.service.LandTitleAssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/land-title-assets")
@RequiredArgsConstructor
public class LandTitleAssetController {
    private final LandTitleAssetService landTitleAssetService;

    @GetMapping
    public ResponseEntity<LandTitleAssetResponse> getLandTitleAsset
            (@RequestParam String titleDeedNo, @RequestParam String province) {
        return ResponseEntity.ok(
                LandTitleAssetResponse.from(
                        landTitleAssetService.findLandTitleAssetByTitleDeedNo(titleDeedNo, province)
                )
        );
    }

    @PostMapping
    public ResponseEntity<LandTitleAssetResponse> createLandTitleAsset(@RequestBody LandTitleAssetEntity landTitleAssetEntity) {
        return ResponseEntity.ok(
                LandTitleAssetResponse.from(
                        landTitleAssetService.depositAsset(landTitleAssetEntity)
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<StandardResponse<Void>> deleteByTitleDeed(
            @RequestParam String titleDeedNo,
            @RequestParam String province
    ) {
        landTitleAssetService.deleteLandTitleAssetByTitleDeedNo(titleDeedNo, province);

        return ResponseEntity.ok(
                StandardResponse.success("Land title asset deleted successfully")
        );
    }
}
