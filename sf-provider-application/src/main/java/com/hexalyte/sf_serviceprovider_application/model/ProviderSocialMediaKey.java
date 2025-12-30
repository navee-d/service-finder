package com.hexalyte.sf_serviceprovider_application.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderSocialMediaKey implements Serializable {
    private Long providerId;
    private Long platformId;
}
