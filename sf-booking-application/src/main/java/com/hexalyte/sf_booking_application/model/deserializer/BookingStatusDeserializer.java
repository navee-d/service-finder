package com.hexalyte.sf_booking_application.model.deserializer;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.hexalyte.sf_booking_application.model.BookingStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.util.EnumUtils;

@Component
public class BookingStatusDeserializer extends StdConverter<String, BookingStatus> {
    @Override
    public BookingStatus convert(String s) {
        try {
            return EnumUtils.findEnumInsensitiveCase(BookingStatus.class, s);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a valid booking status");
        }
    }
}
