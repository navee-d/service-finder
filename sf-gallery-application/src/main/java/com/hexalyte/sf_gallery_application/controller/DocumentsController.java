package com.hexalyte.sf_gallery_application.controller;

import com.hexalyte.sf_gallery_application.model.Document;
import com.hexalyte.sf_gallery_application.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentsController {

    private final DocumentService documentService;

    public DocumentsController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public ResponseEntity<List<Document>> getDocuments() {
        List<Document> documents = documentService.getDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        return documentService.getDocumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        return documentService.downloadDocument(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Document>> addDocuments(
            @RequestPart("document") Document document,
            @RequestPart("files") MultipartFile[] files) {

        List<Document> savedDocuments = documentService.addDocuments(document, files)
                .orElse(List.of());
        return new ResponseEntity<>(savedDocuments, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Document> updateDocument(
            @RequestPart("document") Document document,
            @RequestPart("file") MultipartFile file,
            @PathVariable Long id) {

        return documentService.updateDocument(document, file, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{serviceProviderId}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocument(@PathVariable Long serviceProviderId, @PathVariable Long id) {
        documentService.deleteDocument(serviceProviderId, id);
    }

    @DeleteMapping("/{serviceProviderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocumentsByServiceProviderId(@PathVariable Long serviceProviderId) {
        documentService.deleteDocumentsByServiceProviderId(serviceProviderId);
    }
}
