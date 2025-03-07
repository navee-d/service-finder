package com.hexalyte.sf_service_application.controller;

import com.hexalyte.sf_service_application.model.Solution;
import com.hexalyte.sf_service_application.model.SolutionCategory;
import com.hexalyte.sf_service_application.service.SolutionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("services")
public class SolutionController {
    private final SolutionService service;

    public SolutionController(SolutionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Solution>> getServices(){
        return new ResponseEntity<>(service.getServices(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solution> getServiceById(@PathVariable Long id){
        return ResponseEntity.of(service.getServiceById(id));
    }

    @GetMapping("service/category/{id}")
    private ResponseEntity<List<SolutionCategory>> getServiceCategories(@PathVariable Long id){
        return ResponseEntity.of(service.getServiceCategories(id));
    }

    @PostMapping
    private ResponseEntity<Solution> addService(@RequestBody @Valid Solution solution){
        return ResponseEntity.of(service.addService(solution));
    }

    @PutMapping("/{id}")
    private ResponseEntity<Solution> updateService(@PathVariable Long id,@RequestBody @Valid Solution solution){
        return ResponseEntity.of(service.updateService(id, solution));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT,reason = "Service deleted successfully")
    private void deleteService(@PathVariable Long id){
        service.deleteService(id);
    }
}
