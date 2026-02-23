package org.example.assetmanager.asset.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.assetmanager.asset.entities.Asset;
import org.example.assetmanager.asset.enums.AssetStatus;
import org.example.assetmanager.asset.exceptions.EntityNotFoundException;
import org.example.assetmanager.asset.repositories.AssetRepository;
import org.example.assetmanager.user.entity.User;
import org.example.assetmanager.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    public List<?> getAllAssets() {
        return assetRepository.findAll();
    }

    // 1. นำฝากทรัพย์สิน
    public Asset depositAsset(Asset asset, Long userId) {

        if (asset.getName() == null || asset.getName().isEmpty()) {
            throw new IllegalArgumentException("Asset name cannot be empty");
        }

        if (asset.getType() == null) {
            throw new IllegalArgumentException("Asset type cannot be empty");
        }

        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }

        User user = findUser(userId);

        Asset newAsset = Asset.builder()
                .currentUser(user)
                .name(asset.getName())
                .status(AssetStatus.AVAILABLE)
                .type(asset.getType())
                .build();
        return assetRepository.save(newAsset);
    }

    // 2. เบิกทรัพย์สินไปใช้
    public Asset checkoutAsset(Long assetId, Long userId) {

        if (assetId == null || assetId <= 0) {
            throw new IllegalArgumentException("Asset ID cannot be empty");
        }

        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }

        Asset asset = findAsset(assetId);

        if (asset.getStatus() != AssetStatus.AVAILABLE) {
            throw new IllegalStateException("Asset is not available for checkout");
        }

        User user = findUser(userId);

        asset.setStatus(AssetStatus.WITHDRAWN);
        asset.setCurrentUser(user);

        return assetRepository.save(asset);
    }

    // 3. คืนทรัพย์สิน
    public Asset returnAsset(Long assetId, Long userId) {

        if (assetId == null || assetId <= 0) {
            throw new IllegalArgumentException("Asset ID cannot be empty");
        }

        Asset asset = findAsset(assetId);

        findUser(userId);

        if (asset.getStatus() != AssetStatus.WITHDRAWN) {
            throw new IllegalStateException("Asset is not currently in use");
        }

        asset.setStatus(AssetStatus.AVAILABLE);
        asset.setCurrentUser(null);

        return assetRepository.save(asset);
    }

    @Override
    public AssetStatus getAssetStatus(Long assetId) {
        Asset asset = findAsset(assetId);
        return asset.getStatus();
    }

    private Asset findAsset(Long assetId) {

        if (assetId == null || assetId <= 0) {
            throw new IllegalArgumentException("Asset ID cannot be empty");
        }

        return assetRepository
                .findById(assetId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Asset", assetId));
    }

    private User findUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("user ID cannot be empty");
        }

        return userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("User", userId));
    }

}
