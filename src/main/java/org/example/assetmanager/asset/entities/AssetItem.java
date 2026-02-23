package org.example.assetmanager.asset.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asset_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssetItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_master_id", nullable = false)
    private ItemMaster itemMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private AssetTransaction transaction;

    public AssetItem(String itemName, AssetTransaction transaction) {
        this.itemName = itemName;
        this.transaction = transaction;
    }
}