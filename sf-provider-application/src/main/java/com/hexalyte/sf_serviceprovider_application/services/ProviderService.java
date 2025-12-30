package com.hexalyte.sf_serviceprovider_application.services;

import com.hexalyte.sf_serviceprovider_application.model.Provider;

import java.util.List;
import java.util.Optional;

public interface ProviderService {

    List<Provider> getProviders();
    Optional<Provider> getProviderById(Long id);
    Optional<Provider> addProvider(Provider provider);
    Optional<Provider> updateProvider(Long id,Provider provider);
    void deleteProvider(Long id);

}
