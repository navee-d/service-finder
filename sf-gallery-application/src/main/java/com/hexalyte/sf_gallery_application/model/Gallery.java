package com.hexalyte.sf_gallery_application.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "gallery")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GalleryID")
    private Long galleryId;

    @Column(name = "ServiceProviderID")
    private Long serviceProviderId;

    @Column(name = "ImageURL", nullable = false,updatable = false)
    @Length(max = 255,message = "Image URL cannot exceed 255 characters")
    private String imageUrl;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP",updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
