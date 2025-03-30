package com.hexalyte.sf_service_application.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexalyte.sf_service_application.model.Solution;
import com.hexalyte.sf_service_application.repository.SolutionRepository;
import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
@Profile("development")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SolutionConfig implements CommandLineRunner {

    private final SolutionRepository solutionRepository;
    private final ObjectMapper objectMapper;

    @Getter
    private List<Solution> solutions;

    public SolutionConfig(SolutionRepository solutionRepository, ObjectMapper objectMapper) {
        this.solutionRepository = solutionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        solutions = solutionRepository.saveAll(objectMapper.readValue(TypeReference.class.getResourceAsStream("/Data/solutions.json"),
                new TypeReference<List<Solution>>() {}));

    }
}
