package com.hexalyte.sf_service_application.model.deserializer;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.hexalyte.sf_service_application.model.SubCategory;
import com.hexalyte.sf_service_application.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class SubCategoryConverter extends StdConverter<Integer, SubCategory> {

    @Autowired
    private SubCategoryRepository repository;

    @Override
    public SubCategory convert(Integer value) {
        return repository.findById(value).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find subcategory with such ID")
        );
    }
}
