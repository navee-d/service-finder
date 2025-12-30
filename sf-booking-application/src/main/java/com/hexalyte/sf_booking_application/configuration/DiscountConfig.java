package com.hexalyte.sf_booking_application.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexalyte.sf_booking_application.model.Discount;
import com.hexalyte.sf_booking_application.repository.DiscountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
@Profile("development")
public class DiscountConfig implements CommandLineRunner {

    private final DiscountRepository repository;
    private final ObjectMapper mapper;

    public DiscountConfig(DiscountRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void run(String... args) throws Exception {
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/Data/Discount.json")){
            repository.saveAll(mapper.readValue(inputStream, new TypeReference<List<Discount>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
