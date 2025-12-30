package com.hexalyte.sf_serviceprovider_application.repository;

import com.hexalyte.sf_serviceprovider_application.model.ProviderSocialMedia;
import com.hexalyte.sf_serviceprovider_application.model.ProviderSocialMediaKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderSocialMediaRepository extends JpaRepository<ProviderSocialMedia, ProviderSocialMediaKey> {
    Optional<ProviderSocialMedia> findByProvider_ProviderId(Long providerId);
    Optional<ProviderSocialMedia> findBySocialMediaPlatform_PlatformID(Long id);
    void deleteAllByProvider_ProviderId(Long id);
    void deleteAllBySocialMediaPlatform_PlatformID(Long platformId);
}
