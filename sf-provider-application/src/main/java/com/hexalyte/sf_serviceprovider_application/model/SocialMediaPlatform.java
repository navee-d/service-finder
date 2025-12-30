package com.hexalyte.sf_serviceprovider_application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Entity
@Data
@Table(name = "socialmediaplatforms")
public class SocialMediaPlatform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlatformID")
    private Long platformID;

    @Column(name = "Name", length = 50, nullable = false)
    @NotNull(message = "Name cannot be null")
    @Length(max = 20,message = "Name cannot exceed 20 characters")
    private String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
}
