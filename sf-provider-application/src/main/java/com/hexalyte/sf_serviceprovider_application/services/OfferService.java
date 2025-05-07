package com.hexalyte.sf_serviceprovider_application.services;

import com.hexalyte.sf_serviceprovider_application.model.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferService {

    List<Offer> getOffers();
    Optional<Offer> getOfferById(Long id);
    Optional<List<Offer>> getOfferByProvider(Long id);
    Optional<Offer> addOffer(Offer offer);
    Optional<Offer> updateOffer(Long id, Offer offer);
    void deleteOffer(Long id);
}
