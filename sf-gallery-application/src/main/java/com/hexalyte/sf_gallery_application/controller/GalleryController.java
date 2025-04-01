package com.hexalyte.sf_gallery_application.controller;

import com.hexalyte.sf_gallery_application.model.Gallery;
import com.hexalyte.sf_gallery_application.service.GalleryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("gallery")
public class GalleryController {

    private final GalleryService service;

    public GalleryController(GalleryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Gallery>> getGalleries() {
        return new ResponseEntity<>(service.getGalleries(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gallery> getGalleryById(@PathVariable Long id) {
        return ResponseEntity.of(service.getGalleryById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Optional<List<Gallery>>> addImage(Gallery gallery, @RequestParam("images") MultipartFile[] images) {
        return new ResponseEntity<>(service.addGallery(gallery, images), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Gallery> updateGallery(Gallery gallery, @RequestParam("image") MultipartFile image, @PathVariable Long id) {
        return ResponseEntity.of(service.updateGallery(gallery, image, id));
    }

    @DeleteMapping("/{serviceProviderId}/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Image deleted successfully")
    public void deleteGallery(@PathVariable Long id) {
        service.deleteGallery(id);
    }

    @DeleteMapping("/{serviceProviderId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Gallery deleted successfully")
    public void deleteGalleryByServiceProviderId(@PathVariable Long serviceProviderId) {
        service.deleteGalleryByServiceProviderId(serviceProviderId);
    }
}
