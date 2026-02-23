package org.example.assetmanager;

import org.example.assetmanager.asset.entities.Asset;
import org.example.assetmanager.asset.enums.AssetStatus;
import org.example.assetmanager.asset.enums.AssetType;
import org.example.assetmanager.asset.exceptions.EntityNotFoundException;
import org.example.assetmanager.asset.repositories.AssetRepository;
import org.example.assetmanager.asset.services.AssetServiceImpl;
import org.example.assetmanager.user.entity.User;
import org.example.assetmanager.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Mock
    private UserRepository userRepository;

    // ----- 1. Deposit -----
    @Test
    void depositAsset_shouldSetStatusAvailable() {
        AssetServiceImpl assetService = new AssetServiceImpl(assetRepository, userRepository);

        User user = User.builder()
                .id(1L)
                .firstName("Kriangkrai")
                .lastName("Ketkun")
                .build();

        Asset asset = Asset.builder()
                .name("Laptop")
                .type(AssetType.VEHICLE)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(assetRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Asset result = assetService.depositAsset(asset, 1L);

        assertEquals(AssetStatus.AVAILABLE, result.getStatus());
        assertEquals(user, result.getCurrentUser());
        assertEquals(1L, result.getCurrentUser().getId());
        verify(assetRepository).save(any());
    }

    // ----- 2. CheckOut -----
    @Test
    void checkoutAsset_success() {
        AssetServiceImpl assetService = new AssetServiceImpl(assetRepository, userRepository);

        User user = User.builder()
                .id(1L)
                .firstName("Kriangkrai")
                .lastName("Ketkun")
                .build();

        Asset asset = Asset.builder()
                .id(1L)
                .status(AssetStatus.AVAILABLE)
                .build();

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(assetRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Asset result = assetService.checkoutAsset(1L, 1L);

        assertEquals(AssetStatus.WITHDRAWN, result.getStatus());
        assertEquals(user, result.getCurrentUser());
        assertEquals("Kriangkrai", result.getCurrentUser().getFirstName());
    }

    @Test
    void checkoutAsset_whenNotAvailable_shouldThrowException() {
        AssetServiceImpl assetService = new AssetServiceImpl(assetRepository, userRepository);

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
        AssetServiceImpl assetService = new AssetServiceImpl(assetRepository, userRepository);

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
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(assetRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Asset result = assetService.returnAsset(1L, 1L);
        assertEquals(AssetStatus.AVAILABLE, result.getStatus());
        assertNull(result.getCurrentUser());
    }

    @Test
    void returnAsset_whenNotInUse_shouldThrowException() {
        AssetServiceImpl assetService = new AssetServiceImpl(assetRepository, userRepository);

        User user = User.builder()
                .id(1L)
                .build();

        Asset asset = Asset.builder()
                .id(1L)
                .status(AssetStatus.AVAILABLE)
                .build();

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> assetService.returnAsset(1L, 1L)
        );
        assertEquals("Asset is not currently in use", ex.getMessage());
    }

    // ----- 4. Not Found -----
    @Test
    void assetNotFound_shouldThrowException() {
        AssetServiceImpl assetService = new AssetServiceImpl(assetRepository, userRepository);

        when(assetRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> assetService.checkoutAsset(99L, 99L)
        );
        assertTrue(ex.getMessage().contains("Asset not found"));
    }

}
