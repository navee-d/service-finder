package com.hexalyte.sf_serviceprovider_application.controller;


import com.hexalyte.sf_serviceprovider_application.model.ProviderSocialMedia;
import com.hexalyte.sf_serviceprovider_application.model.SocialMediaPlatform;
import com.hexalyte.sf_serviceprovider_application.services.SocialMediaPlatformService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("socialmedia")
public class SocialMediaPlatformController {
    private final SocialMediaPlatformService service;

    public SocialMediaPlatformController(SocialMediaPlatformService socialMediaPlatformService, SocialMediaPlatformService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SocialMediaPlatform>> getSocialMediaPlatforms() {
        return new ResponseEntity<>(service.getSocialMediaPlatforms(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<SocialMediaPlatform> getSocialMediaPlatformById(@PathVariable Long id) {
        return ResponseEntity.of(service.getSocialMediaPlatformById(id));
    }

    @GetMapping("socialmedia/provider/{id}")
    public ResponseEntity<Optional<ProviderSocialMedia>> getProviderSocialMedia(@PathVariable Long id) {
        return  ResponseEntity.of(service.getSocialMediaPlatformProviders(id));
    }

    @PostMapping
    public ResponseEntity<SocialMediaPlatform> addSocialMediaPlatform(@RequestBody @Valid SocialMediaPlatform socialMediaPlatform) {
        return ResponseEntity.of(service.addSocialMediaPlatform(socialMediaPlatform));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialMediaPlatform> updateSocialMediaPlatform(@PathVariable Long id,@RequestBody @Valid SocialMediaPlatform socialMediaPlatform) {
        return ResponseEntity.of(service.updateSocialMediaPlatform(id, socialMediaPlatform));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value =  HttpStatus.NO_CONTENT,reason = "SocialvMedia Platform successfully deleted")
    public void deleteSocialMediaPlatform(@PathVariable Long id) {
        service.deleteSocialMediaPlatform(id);
    }
}
