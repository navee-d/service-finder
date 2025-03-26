package com.hexalyte.sf_serviceprovider_application.controller;

import com.hexalyte.sf_serviceprovider_application.model.Provider;
import com.hexalyte.sf_serviceprovider_application.repository.ProviderRepository;
import com.hexalyte.sf_serviceprovider_application.services.ProviderService;
import com.hexalyte.sf_serviceprovider_application.services.impl.ProviderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProviderController {
    private final ProviderServiceImpl service;

    public ProviderController(ProviderServiceImpl service, ProviderRepository providerRepository) {
        this.service = service;
    }

    @GetMapping("/providers")
    public ResponseEntity<List<Provider>> getProviders() {
        return new ResponseEntity<>(service.getProviders(), HttpStatus.OK);
    }

    @GetMapping("/provider/{id}")
    public ResponseEntity<Provider> getProviderByID(@PathVariable Long id) {
        return ResponseEntity.of(service.getProviderById(id));
    }

    @PostMapping("/provider")
    public ResponseEntity<Provider> addProvider(@RequestBody @Valid Provider provider) {
        return ResponseEntity.of(service.addProvider(provider));
    }

    @PutMapping("/provider/{id}")
    public ResponseEntity<Provider> updateProvider(@PathVariable Long id, @RequestBody Provider provider) {
        return ResponseEntity.of(service.updateProvider(id, provider));
    }

    @DeleteMapping("/provider/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT,reason = "Category successfully deleted")
    public void deleteProvider(@PathVariable Long id) {
         service.deleteProvider(id);
    }





}
