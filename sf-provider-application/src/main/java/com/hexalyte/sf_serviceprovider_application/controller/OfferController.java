package com.hexalyte.sf_serviceprovider_application.controller;

import com.hexalyte.sf_serviceprovider_application.model.Offer;
import com.hexalyte.sf_serviceprovider_application.services.impl.OfferServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("offers")
public class OfferController {

    private final OfferServiceImpl service;

    public OfferController(OfferServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers(){
        return new ResponseEntity<>(service.getOffers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferByID(@PathVariable Long id){
        return ResponseEntity.of(service.getOfferById(id));
    }

    @GetMapping("/provider/{id}")
    public ResponseEntity<List<Offer>> getOfferByProvider(@PathVariable Long id){
        return ResponseEntity.of(service.getOfferByProvider(id));
    }

    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody @Valid Offer offer){
        return ResponseEntity.of(service.addOffer(offer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @RequestBody Offer offer){
        return ResponseEntity.of(service.updateOffer(id, offer));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT,reason = "Offer Successfully Deleted!")
    public void deleteOffer(@PathVariable Long id){
        service.deleteOffer(id);
    }

}
