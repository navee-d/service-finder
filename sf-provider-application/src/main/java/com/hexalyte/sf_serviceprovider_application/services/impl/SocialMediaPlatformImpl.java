package com.hexalyte.sf_serviceprovider_application.services.impl;

import com.hexalyte.sf_serviceprovider_application.model.ProviderSocialMedia;
import com.hexalyte.sf_serviceprovider_application.model.SocialMediaPlatform;
import com.hexalyte.sf_serviceprovider_application.repository.ProviderSocialMediaRepository;
import com.hexalyte.sf_serviceprovider_application.repository.SocialMediaPlatformRepository;
import com.hexalyte.sf_serviceprovider_application.services.SocialMediaPlatformService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SocialMediaPlatformImpl implements SocialMediaPlatformService {

    private final SocialMediaPlatformRepository socialMediaPlatformRepository;
    private final ProviderSocialMediaRepository providerSocialMEdiaRepository;

    public SocialMediaPlatformImpl(SocialMediaPlatformRepository socialMediaPlatformRepository, ProviderSocialMediaRepository providerSocialMEdiaRepository) {
        this.socialMediaPlatformRepository = socialMediaPlatformRepository;
        this.providerSocialMEdiaRepository = providerSocialMEdiaRepository;
    }

    @Override
    public List<SocialMediaPlatform> getSocialMediaPlatforms() {
        return socialMediaPlatformRepository.findAll();
    }

    @Override
    public Optional<SocialMediaPlatform> getSocialMediaPlatformById(Long id) {
        Optional<SocialMediaPlatform> socialMediaPlatform = socialMediaPlatformRepository.findById(id);
        if (socialMediaPlatform.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Social Media Platform not found");
        return socialMediaPlatform;
    }

    @Override
    public Optional<Optional<ProviderSocialMedia>> getSocialMediaPlatformProviders(Long id) {
        return Optional.of(providerSocialMEdiaRepository.findBySocialMediaPlatform_PlatformID(id));
    }

    @Override
    public Optional<SocialMediaPlatform> addSocialMediaPlatform(SocialMediaPlatform socialMediaPlatform) {
        return Optional.of(socialMediaPlatformRepository.save(socialMediaPlatform));
    }

    @Override
    public Optional<SocialMediaPlatform> updateSocialMediaPlatform(Long id, SocialMediaPlatform socialMediaPlatform) {
        if (!socialMediaPlatformRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Social Media Platform not found");
        socialMediaPlatform.setPlatformID(id);
        return Optional.of(socialMediaPlatformRepository.save(socialMediaPlatform));
    }

    @Override
    @Transactional
    public void deleteSocialMediaPlatform(Long id) {
        if (!socialMediaPlatformRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Social Media Platform not found");
        socialMediaPlatformRepository.deleteById(id);
    }
}
