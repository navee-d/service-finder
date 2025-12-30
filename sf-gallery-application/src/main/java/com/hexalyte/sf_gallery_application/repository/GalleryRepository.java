package com.hexalyte.sf_gallery_application.repository;

import com.hexalyte.sf_gallery_application.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    boolean existsByServiceProviderId(Long serviceProviderId);

    void deleteAllByServiceProviderId(Long serviceProviderId);

    List<Gallery> findAllByServiceProviderId(Long serviceProviderId);
}
