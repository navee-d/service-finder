package com.hexalyte.sf_serviceprovider_application.services.impl;

import com.hexalyte.sf_serviceprovider_application.model.Provider;
import com.hexalyte.sf_serviceprovider_application.repository.ProviderRepository;
import com.hexalyte.sf_serviceprovider_application.services.ProviderService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;


    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }


    @Override
    public List<Provider> getProviders() {
        return providerRepository.findAll();
    }

    @Override
    public Optional<Provider> getProviderById(Long id) {
        Optional<Provider> provider = providerRepository.findById(id);
        if (provider.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found");
        return provider;
    }

    @Override
    public Optional<Provider> addProvider(Provider provider) {
        return Optional.of(providerRepository.save(provider));
    }

    @Override
    public Optional<Provider> updateProvider(Long id,Provider provider) {
        if (!providerRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found");
        provider.setProviderId(id);
        return Optional.of(providerRepository.save(provider));
    }

    @Override
    @Transactional
    public void deleteProvider(Long id) {
        if (!providerRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found");
        providerRepository.deleteById(id);

    }
}
