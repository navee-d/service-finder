package com.hexalyte.sf_serviceprovider_application.repository;

import com.hexalyte.sf_serviceprovider_application.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer,Long> {
    Optional<List<Offer>> findAllByProvider_ProviderId(Long providerProviderId);
}
