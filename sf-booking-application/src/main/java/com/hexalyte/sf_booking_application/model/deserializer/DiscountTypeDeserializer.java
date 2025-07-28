package com.hexalyte.sf_booking_application.model.deserializer;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.hexalyte.sf_booking_application.model.DiscountType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.util.EnumUtils;

@Component
public class DiscountTypeDeserializer extends StdConverter<String, DiscountType> {

    @Override
    public DiscountType convert(String s) {
        try {
            return EnumUtils.findEnumInsensitiveCase(DiscountType.class, s);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a valid discount type");
        }
    }
}
