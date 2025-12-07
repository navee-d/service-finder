package com.hexalyte.sf_gallery_application.service.impl;

import com.hexalyte.sf_gallery_application.model.Document;
import com.hexalyte.sf_gallery_application.repository.DocumentRepository;
import com.hexalyte.sf_gallery_application.service.DocumentService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final MinioClient minioClient;
    private final Map<String,String> docContentTypes = new HashMap<>();

    @Value("#{${object-storage.doc-part-size}}")
    private Long docPartSize;

    public DocumentServiceImpl(DocumentRepository documentRepository, MinioClient minioClient) {
        this.documentRepository = documentRepository;
        this.minioClient = minioClient;
        docContentTypes.put("application/pdf",".pdf");
        docContentTypes.put("application/vnd.ms-excel",".xls");
        docContentTypes.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",".xlsx");
    }

    @Override
    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public Optional<Document> getDocumentById(Long id) {
        if (!documentRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
        return documentRepository.findById(id);
    }

    @Override
    public Optional<List<Document>> addDocuments(Document document, MultipartFile[] files) {

        List<Document> fileList = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "documents are not attached");
            } else if (!docContentTypes.containsKey(file.getContentType())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Document file is not pdf or spreadsheet");
            }

            try {
                String objectName = document.getServiceProviderId() + "/file" + new Date().getTime();
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket("documents")
                                .object(objectName)
                                .stream(file.getInputStream(), -1, docPartSize)
                                .contentType(file.getContentType())
                                .build()
                );

                Document documentItem = Document.builder()
                        .serviceProviderId(document.getServiceProviderId())
                        .description(document.getDescription())
                        .documentUrl(objectName)
                        .contentType(file.getContentType())
                        .build();
                fileList.add(documentItem);

            } catch (ErrorResponseException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service error: " + e.getMessage());
            } catch (InsufficientDataException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient data available in the inputstream");
            } catch (InternalException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal library error: " + e.getMessage());
            } catch (InvalidKeyException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing HMAC SHA-256 library");
            } catch (InvalidResponseException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service returned invalid/no error response: " + e.getMessage());
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "I/O error on S3 operation: " + e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing MD5 or SHA-256 digest library");
            } catch (ServerException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "HTTP server error: " + e.getMessage());
            } catch (XmlParserException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "XML server error: " + e.getMessage());
            }

        }
        return Optional.of(documentRepository.saveAll(fileList));
    }


    @Override
    public Optional<Document> updateDocument(Document document, MultipartFile file, Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document with such ID is not found");
        } else if (!docContentTypes.containsKey(file.getContentType())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Document file is not pdf or spreadsheet");
        }

        Document doc = documentRepository.getReferenceById(id);
        doc.setDescription(document.getDescription());
        doc.setContentType(file.getContentType());

        if (!file.isEmpty()) {
            try {
                String objectName = doc.getDocumentUrl();
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket("documents")
                                .object(objectName)
                                .stream(file.getInputStream(), -1, docPartSize)
                                .contentType(file.getContentType())
                                .build()
                );
            } catch (ErrorResponseException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service error: " + e.getMessage());
            } catch (InsufficientDataException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient data available in the inputstream");
            } catch (InternalException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal library error: " + e.getMessage());
            } catch (InvalidKeyException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing HMAC SHA-256 library");
            } catch (InvalidResponseException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service returned invalid/no error response: " + e.getMessage());
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "I/O error on S3 operation: " + e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing MD5 or SHA-256 digest library");
            } catch (ServerException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "HTTP server error: " + e.getMessage());
            } catch (XmlParserException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "XML server error: " + e.getMessage());
            }
        }

        return Optional.of(documentRepository.save(doc));
    }


    @Override
    public void deleteDocument(Long serviceProviderId, Long id) {
        if (!documentRepository.existsByServiceProviderId(serviceProviderId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document with such service provider ID is not found");
        else if (!documentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document with such ID under this service provider is not found");
        }

        try {
            String objectName = documentRepository.getReferenceById(id).getDocumentUrl();
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket("documents")
                            .object(objectName)
                            .build()
            );
        } catch (ErrorResponseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service error: " + e.getMessage());
        } catch (InsufficientDataException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Insufficient data available in the Document object inputstream");
        } catch (InternalException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal library error: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing HMAC SHA-256 library");
        } catch (InvalidResponseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service returned invalid/no error response: " + e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "I/O error on S3 operation: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing MD5 or SHA-256 digest library");
        } catch (ServerException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "HTTP server error: " + e.getMessage());
        } catch (XmlParserException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "XML server error: " + e.getMessage());
        }
        documentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteDocumentsByServiceProviderId(Long serviceProviderId) {
        if (!documentRepository.existsByServiceProviderId(serviceProviderId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Documents with such service provider ID cannot found");

        LinkedList<DeleteObject> deleteObjects = new LinkedList<>();
        documentRepository.findAllByServiceProviderId(serviceProviderId).forEach(document -> {
            deleteObjects.add(new DeleteObject(document.getDocumentUrl()));
        });

        minioClient.removeObjects(
                RemoveObjectsArgs.builder()
                        .bucket("documents")
                        .objects(deleteObjects)
                        .build()
        ).forEach(
                deleteErrorResult -> {
                    try {
                        deleteErrorResult.get();
                    } catch (ErrorResponseException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service error: " + e.getMessage());
                    } catch (InsufficientDataException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Insufficient data available in the document object inputstream");
                    } catch (InternalException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal library error: " + e.getMessage());
                    } catch (InvalidKeyException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing HMAC SHA-256 library");
                    } catch (InvalidResponseException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service returned invalid/no error response: " + e.getMessage());
                    } catch (IOException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "I/O error on S3 operation: " + e.getMessage());
                    } catch (NoSuchAlgorithmException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing MD5 or SHA-256 digest library");
                    } catch (ServerException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "HTTP server error: " + e.getMessage());
                    } catch (XmlParserException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "XML server error: " + e.getMessage());
                    }
                }
        );

        documentRepository.deleteAllByServiceProviderId(serviceProviderId);
    }

    @Override
    public ResponseEntity<byte[]> downloadDocument(Long id) {
        if (!documentRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document with such ID is not found");

        try {
            Document document = documentRepository.getReferenceById(id);
            String home = System.getProperty("user.home");
            String filename = home + File.separator + "Downloads" + File.separator + "download" + new Date().getTime() + docContentTypes.get(document.getContentType());
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket("documents")
                            .object(document.getDocumentUrl())
                            .filename(filename)
                            .build()
            );
        } catch (ErrorResponseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service error: " + e.getMessage());
        } catch (InsufficientDataException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Insufficient data available in the document object inputstream");
        } catch (InternalException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal library error: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing HMAC SHA-256 library");
        } catch (InvalidResponseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service returned invalid/no error response: " + e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "I/O error on S3 operation: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing MD5 or SHA-256 digest library");
        } catch (ServerException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "HTTP server error: " + e.getMessage());
        } catch (XmlParserException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "XML server error: " + e.getMessage());
        }

        return null;
    }
}
