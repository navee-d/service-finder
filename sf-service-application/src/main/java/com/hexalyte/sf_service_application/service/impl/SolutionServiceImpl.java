package com.hexalyte.sf_service_application.service.impl;

import com.hexalyte.sf_service_application.model.Solution;
import com.hexalyte.sf_service_application.repository.SolutionRepository;
import com.hexalyte.sf_service_application.service.SolutionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolutionServiceImpl implements SolutionService {

    private final SolutionRepository repository;

    public SolutionServiceImpl(SolutionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Solution> getServices() {
        return repository.findAll();
    }

    @Override
    public Optional<Solution> getServiceById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void addService(Solution solution) {
        repository.save(solution);
    }

    @Override
    public void updateService(Solution solution) {
        repository.save(solution);
    }

    @Override
    public void deleteService(Long id) {
        repository.deleteById(id);
    }

}
