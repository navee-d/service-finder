package com.hexalyte.sf_gallery_application.repository;

import com.hexalyte.sf_gallery_application.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document,Long> {
    boolean existsByServiceProviderId(Long serviceProviderId);

    List<Document> findAllByServiceProviderId(Long serviceProviderId);

    void deleteAllByServiceProviderId(Long serviceProviderId);
}
