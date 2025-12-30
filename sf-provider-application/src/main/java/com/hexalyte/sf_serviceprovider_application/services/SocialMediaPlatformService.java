package com.hexalyte.sf_serviceprovider_application.services;

import com.hexalyte.sf_serviceprovider_application.model.ProviderSocialMedia;
import com.hexalyte.sf_serviceprovider_application.model.SocialMediaPlatform;

import java.util.List;
import java.util.Optional;

public interface SocialMediaPlatformService {

    List<SocialMediaPlatform> getSocialMediaPlatforms();
    Optional<SocialMediaPlatform> getSocialMediaPlatformById(Long id);
    Optional<Optional<ProviderSocialMedia>> getSocialMediaPlatformProviders(Long id);
    Optional<SocialMediaPlatform> addSocialMediaPlatform(SocialMediaPlatform socialMediaPlatform);
    Optional<SocialMediaPlatform> updateSocialMediaPlatform(Long id, SocialMediaPlatform socialMediaPlatform);
    void deleteSocialMediaPlatform(Long id);

}
