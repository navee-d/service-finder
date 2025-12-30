package com.hexalyte.sf_service_application.controller;

import com.hexalyte.sf_service_application.model.CategorySolution;
import com.hexalyte.sf_service_application.model.Solution;
import com.hexalyte.sf_service_application.model.SubCategorySolution;
import com.hexalyte.sf_service_application.service.SolutionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("services")
public class SolutionController {
    private final SolutionService service;

    public SolutionController(SolutionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Solution>> getServices(){
        return ResponseEntity.ofNullable(service.getServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solution> getServiceById(@PathVariable Integer id){
        return ResponseEntity.of(service.getServiceById(id));
    }

    @GetMapping("category/{id}")
    private ResponseEntity<List<CategorySolution>> getServiceCategories(@PathVariable Integer id){
        return ResponseEntity.ofNullable(service.getServiceCategories(id));
    }

    @GetMapping("subcategory/{id}")
    private ResponseEntity<List<SubCategorySolution>> getServiceSubCategories(@PathVariable Integer id){
        return ResponseEntity.ofNullable(service.getServiceSubCategories(id));
    }

    @PostMapping
    private ResponseEntity<Optional<Solution>> addService(@RequestBody @Valid Solution solution){
        return new ResponseEntity<>(service.addService(solution),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Solution> updateService(@PathVariable Integer id,@RequestBody @Valid Solution solution){
        return ResponseEntity.of(service.updateService(id, solution));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT,reason = "Service deleted successfully")
    private void deleteService(@PathVariable Integer id){
        service.deleteService(id);
    }
}
