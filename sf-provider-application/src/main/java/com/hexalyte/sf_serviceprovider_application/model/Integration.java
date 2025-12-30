package com.hexalyte.sf_serviceprovider_application.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID; // FIX: Imported UUID

@Data
@Entity
@Table(name = "integrations")
public class Integration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IntegrationID")
    private Integer integrationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ServiceProviderID")
    private Provider provider;

    // FIX: Changed to UUID to store Keycloak ID
    @Column(name = "UserID", columnDefinition = "binary(16)")
    private UUID userId;

    @Column(name = "GoogleCalendar")
    private boolean googleCalendar;

    @Column(name = "SocialMedia")
    private boolean socialMedia;

    @Column(name = "Chat")
    private boolean chat;

    @CreationTimestamp
    @Column(name = "CreatedAt", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;
}