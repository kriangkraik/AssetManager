package org.example.assetmanager.asset.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.assetmanager.asset.enums.TransactionType;
import org.example.assetmanager.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "asset_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ทรัพย์สินที่เกี่ยวข้อง
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    // คนทำรายการ
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ประเภทเหตุการณ์
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    // วันที่ทำรายการ
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    // หมายเหตุ
    private String remark;

    // constructor
    public AssetTransaction(Asset asset,
                            User user,
                            TransactionType transactionType,
                            LocalDateTime transactionDate,
                            String remark) {
        this.asset = asset;
        this.user = user;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.remark = remark;
    }

}
