package com.hexalyte.sf_serviceprovider_application.services.impl;

import com.hexalyte.sf_serviceprovider_application.model.Offer;
import com.hexalyte.sf_serviceprovider_application.repository.OfferRepository;
import com.hexalyte.sf_serviceprovider_application.services.OfferService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> getOffers() {
        return offerRepository.findAll();
    }

    @Override
    public Optional<Offer> getOfferById(Long id) {
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found");
        return offer;
    }

    @Override
    public Optional<List<Offer>> getOfferByProvider(Long id) {
        Optional<List<Offer>> offer = offerRepository.findAllByProvider_ProviderId(id);
        if (offer.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found");
        return offer;
    }

    @Override
    public Optional<Offer> addOffer(Offer offer) {
        return Optional.of(offerRepository.save(offer));
    }

    @Override
    public Optional<Offer> updateOffer(Long id, Offer offer) {
        if (!offerRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found");
        offer.setOfferId(id);
        return Optional.of(offerRepository.save(offer));
    }

    @Override
    @Transactional
    public void deleteOffer(Long id) {
        if (!offerRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found");
        offerRepository.deleteById(id);
    }
}
