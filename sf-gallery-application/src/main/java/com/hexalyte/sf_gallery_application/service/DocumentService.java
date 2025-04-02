package com.hexalyte.sf_gallery_application.service;

import com.hexalyte.sf_gallery_application.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    List<Document> getDocuments();

    Optional<Document> getDocumentById(Long id);

    Optional<List<Document>> addDocuments(Document document, MultipartFile[] files);

    Optional<Document> updateDocument(Document document, MultipartFile file, Long id);

    void deleteDocument(Long serviceProviderId, Long id);

    void deleteDocumentsByServiceProviderId(Long serviceProviderId);

    void downloadDocument(Long id);
}
