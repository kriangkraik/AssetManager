package org.example.assetmanager;

import jakarta.persistence.EntityNotFoundException;
import org.example.assetmanager.asset.entities.Asset;
import org.example.assetmanager.asset.enums.AssetStatus;
import org.example.assetmanager.asset.enums.AssetType;
import org.example.assetmanager.asset.repositories.AssetRepository;
import org.example.assetmanager.asset.services.AssetService;
import org.example.assetmanager.user.entity.User;
import org.example.assetmanager.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssetServiceTest {
    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    @InjectMocks
    private UserService userService;

    // ----- 1. Deposit -----
    @Test
    void depositAsset_shouldSetStatusAvailable() {
        Asset asset = Asset.builder()
                .name("Laptop")
                .type(AssetType.VEHICLE)
                .build();


        when(assetRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        Asset result = assetService.depositAsset(asset, 1L);
        assertEquals(AssetStatus.AVAILABLE, result.getStatus());
        assertNull(result.getCurrentUser());
        verify(assetRepository).save(any());
    }

    // ----- 2. CheckOut -----
    @Test
    void checkoutAsset_success() {
        Asset asset = Asset.builder()
                .id(1L)
                .status(AssetStatus.AVAILABLE)
                .build();

        when(assetRepository.findById(any())).thenReturn(Optional.of(asset));
        Asset result = assetService.checkoutAsset(1L, 1L);
        assertEquals(AssetStatus.WITHDRAWN, result.getStatus());
        assertEquals("Kriangkrai Ketkun", result.getCurrentUser());
    }

    @Test
    void checkoutAsset_whenNotAvailable_shouldThrowException() {
        Asset asset = Asset.builder()
                .id(1L)
                .status(AssetStatus.WITHDRAWN)
                .build();

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> assetService.checkoutAsset(1L, 1L)
        );
        assertEquals("Asset is not available for checkout", ex.getMessage());
    }

    // ----- 3. Return -----
    @Test
    void returnAsset_success() {
        User user = User.builder()
                .id(1L)
                .email("test1@bkk.co.th")
                .firstName("Kriangkrai")
                .lastName("YuYen")
                .username("test1")
                .password("test1")
                .build();

        Asset asset = Asset.builder()
                .id(1L)
                .status(AssetStatus.WITHDRAWN)
                .currentUser(user)
                .build();

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        Asset result = assetService.returnAsset(1L, 1L);
        assertEquals(AssetStatus.AVAILABLE, result.getStatus());
        assertNull(result.getCurrentUser());
    }

    @Test
    void returnAsset_whenNotInUse_shouldThrowException() {
        Asset asset = Asset.builder()
                .id(1L)
                .status(AssetStatus.AVAILABLE)
                .build();

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> assetService.returnAsset(1L, 1L)
        );
        assertEquals("Asset is not currently in use", ex.getMessage());
    }

    // ----- 4. Not Found -----
    @Test
    void assetNotFound_shouldThrowException() {
        when(assetRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> assetService.checkoutAsset(99L, 99L)
        );
        assertTrue(ex.getMessage().contains("Asset not found"));
    }

}
