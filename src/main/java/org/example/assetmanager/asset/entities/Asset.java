package org.example.assetmanager.asset.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.assetmanager.asset.enums.AssetStatus;
import org.example.assetmanager.asset.enums.AssetType;
import org.example.assetmanager.asset.enums.TransactionType;
import org.example.assetmanager.user.entity.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AssetType type;

    @ManyToOne
    @JoinColumn(name = "current_user_id")
    private User currentUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AssetStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL)
    private List<AssetTransaction> transactions = new ArrayList<>();

    public void withdraw(User user, String remark) {
        if (this.status != AssetStatus.AVAILABLE) {
            throw new IllegalStateException("Asset already withdrawn");
        }

        this.status = AssetStatus.WITHDRAWN;

        AssetTransaction tx = new AssetTransaction(this, user, TransactionType.WITHDRAW, LocalDateTime.now(), remark);

        this.transactions.add(tx);
    }


}
