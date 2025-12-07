package com.hexalyte.sf_gallery_application.controller;

import com.hexalyte.sf_gallery_application.model.Gallery;
import com.hexalyte.sf_gallery_application.service.GalleryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/gallery")
public class GalleryController {

    private final GalleryService galleryService;

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping
    public ResponseEntity<List<Gallery>> getGalleries() {
        List<Gallery> galleries = galleryService.getGalleries();
        return new ResponseEntity<>(galleries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gallery> getGalleryById(@PathVariable Long id) {
        return galleryService.getGalleryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) {
        return galleryService.downloadImage(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Gallery>> addGallery(
            @RequestPart("gallery") Gallery gallery,
            @RequestPart("images") MultipartFile[] images) {

        List<Gallery> savedGalleries = galleryService.addGallery(gallery, images)
                .orElse(List.of());
        return new ResponseEntity<>(savedGalleries, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Gallery> updateGallery(
            @RequestPart("gallery") Gallery gallery,
            @RequestPart("image") MultipartFile image,
            @PathVariable Long id) {

        return galleryService.updateGallery(gallery, image, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{serviceProviderId}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Long serviceProviderId, @PathVariable Long id) {
        galleryService.deleteImage(serviceProviderId, id);
    }

    @DeleteMapping("/{serviceProviderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGalleryByServiceProviderId(@PathVariable Long serviceProviderId) {
        galleryService.deleteGalleryByServiceProviderId(serviceProviderId);
    }
}
