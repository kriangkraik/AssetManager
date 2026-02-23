package org.example.assetmanager.asset.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.assetmanager.asset.enums.AssetType;

@Entity
@Table(name = "item_master")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetType assetType;

    public ItemMaster(String name, AssetType assetType) {
        this.name = name;
        this.assetType = assetType;
    }
}
