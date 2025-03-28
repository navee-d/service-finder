package com.hexalyte.sf_gallery_application.repository;

import com.hexalyte.sf_gallery_application.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentsRepository extends JpaRepository<Gallery,Long> {
}
