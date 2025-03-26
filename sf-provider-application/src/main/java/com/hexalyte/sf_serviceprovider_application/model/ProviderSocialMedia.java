package com.hexalyte.sf_serviceprovider_application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "serviceprovidersocialmedia")
public class ProviderSocialMedia {

    @EmbeddedId
    private ProviderSocialMediaKey id;

    @ManyToOne
    @MapsId("providerId")
    @JoinColumn(name = "ServiceProviderID")
    @JsonIgnoreProperties("serviceprovidersocialmedia")
    private Provider provider;

    @ManyToOne
    @MapsId("platformId")
    @JoinColumn(name = "PlatformID")
    @JsonIgnoreProperties("serviceprovidersocialmedia")
    private SocialMediaPlatform socialMediaPlatform;

    @Column(name = "Link", nullable = false)
    @NotNull(message = "Link cannot be null")
    private String link;


}
