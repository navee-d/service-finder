package com.hexalyte.sf_booking_application.model.deserializer;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.hexalyte.sf_booking_application.model.Discount;
import com.hexalyte.sf_booking_application.repository.DiscountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class DiscountDeserializer extends StdConverter<Long, Discount> {

    private final DiscountRepository repository;

    public DiscountDeserializer(DiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Discount convert(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find discount under such ID")
        );
    }
}
