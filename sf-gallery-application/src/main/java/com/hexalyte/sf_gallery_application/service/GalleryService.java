package com.hexalyte.sf_gallery_application.service;

import com.hexalyte.sf_gallery_application.model.Gallery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface GalleryService {
    List<Gallery> getGalleries();

    Optional<Gallery> getGalleryById(Long id);

    Optional<List<Gallery>> addGallery(Gallery gallery, MultipartFile[] image);

    Optional<Gallery> updateGallery(Gallery gallery, MultipartFile image, Long id);

    void deleteImage(Long serviceProviderId, Long id);

    void deleteGalleryByServiceProviderId(Long serviceProviderId);

    void downloadImage(Long id);
}
