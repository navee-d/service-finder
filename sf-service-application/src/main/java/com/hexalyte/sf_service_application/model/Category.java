package com.hexalyte.sf_service_application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public record Category(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "CategoryID")
        Long categoryId,

        @Column(name = "Name", length = 50, nullable = false)
        String name,

        @Column(name = "Description", columnDefinition = "TEXT")
        String description
) {
}
