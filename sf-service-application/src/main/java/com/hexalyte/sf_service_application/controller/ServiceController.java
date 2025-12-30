package com.hexalyte.sf_service_application.controller;

import com.hexalyte.sf_service_application.model.CategorySolution;
import com.hexalyte.sf_service_application.model.Service;
import com.hexalyte.sf_service_application.model.SubCategorySolution;
import com.hexalyte.sf_service_application.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("services")
public class ServiceController {
    private final ServiceService service;

    public ServiceController(ServiceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Service>> getServices(){
        return ResponseEntity.ofNullable(service.getServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Integer id){
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
    private ResponseEntity<Optional<Service>> addService(@RequestBody @Valid Service service){
        return new ResponseEntity<>(this.service.addService(service),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Service> updateService(@PathVariable Integer id, @RequestBody @Valid Service service){
        return ResponseEntity.of(this.service.updateService(id, service));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT,reason = "Service deleted successfully")
    private void deleteService(@PathVariable Integer id){
        service.deleteService(id);
    }
}
