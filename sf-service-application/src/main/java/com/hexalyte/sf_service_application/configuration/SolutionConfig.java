package com.hexalyte.sf_service_application.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexalyte.sf_service_application.model.Solution;
import com.hexalyte.sf_service_application.repository.SolutionRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.InputStream;
import java.util.List;

@Configuration
@Profile("development")
public class SolutionConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final SolutionRepository repository;
    private final ObjectMapper mapper;

    public SolutionConfig(SolutionRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try(InputStream inputStream = TypeReference.class.getResourceAsStream("/Data/services.json")){
            repository.saveAll(mapper.readValue(inputStream, new TypeReference<List<Solution>>() {
            }));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
