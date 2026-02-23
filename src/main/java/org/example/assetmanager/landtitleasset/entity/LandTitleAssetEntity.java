package org.example.assetmanager.landtitleasset.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.assetmanager.asset.entities.Asset;

@Entity
@Table(name = "land_title_assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LandTitleAssetEntity {
    @Id
    private Long assetId;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Column(name = "title_deed_no", nullable = false, length = 20)
    private String titleDeedNo;

    @Column(name = "province", nullable = false, length = 50)
    private String province;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "codefinance", nullable = false, length = 50)
    private String codefinance;


}
