package com.hexalyte.sf_gallery_application.controller;

import com.hexalyte.sf_gallery_application.model.Document;
import com.hexalyte.sf_gallery_application.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("documents")
public class DocumentsController {

    private final DocumentService service;

    public DocumentsController(DocumentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Document>> getDocuments() {
        return new ResponseEntity<>(service.getDocuments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        return ResponseEntity.of(service.getDocumentById(id));
    }

    @GetMapping("download/{id}")
    public void downloadDocument(@PathVariable Long id) {
        service.downloadDocument(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Optional<List<Document>>> addDocuments(Document document, @RequestParam("files") MultipartFile[] files) {
        return new ResponseEntity<>(service.addDocuments(document, files), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Document> updateDocument(Document document, @RequestParam("file") MultipartFile file, @PathVariable Long id) {
        return ResponseEntity.of(service.updateDocument(document, file, id));
    }

    @DeleteMapping("/{serviceProviderId}/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Document deleted successfully")
    public void deleteDocument(@PathVariable Long serviceProviderId, @PathVariable Long id) {
        service.deleteDocument(serviceProviderId, id);
    }

    @DeleteMapping("/{serviceProviderId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Documents deleted successfully")
    public void deleteDocumentsByServiceProviderId(@PathVariable Long serviceProviderId) {
        service.deleteDocumentsByServiceProviderId(serviceProviderId);
    }
}
