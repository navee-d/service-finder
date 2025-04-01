package com.hexalyte.sf_gallery_application.service.impl;

import com.hexalyte.sf_gallery_application.model.Gallery;
import com.hexalyte.sf_gallery_application.repository.GalleryRepository;
import com.hexalyte.sf_gallery_application.service.GalleryService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.errors.*;
import io.minio.messages.DeleteObject;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final MinioClient minioClient;

    @Value("#{${object-storage.image-part-size}}")
    private Long imagePartSize;

    public GalleryServiceImpl(GalleryRepository galleryRepository, MinioClient minioClient) {
        this.galleryRepository = galleryRepository;
        this.minioClient = minioClient;
    }

    @Override
    public List<Gallery> getGalleries() {
        return galleryRepository.findAll();
    }

    @Override
    public Optional<Gallery> getGalleryById(Long id) {
        if (!galleryRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gallery not found");
        return galleryRepository.findById(id);
    }

    @Override
    public Optional<List<Gallery>> addGallery(Gallery gallery, MultipartFile[] images) {

        List<Gallery> imageList = new ArrayList<>();

        for (MultipartFile image : images) {
            if (image.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Images are not attached");
            } else if (!(image.getContentType().equals("image/jpeg") || image.getContentType().equals("image/png"))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image file is not jpeg/png");
            }

            try {
                String objectName = gallery.getServiceProviderId() + "/image" + new Date().getTime();
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket("images")
                                .object(objectName)
                                .stream(image.getInputStream(), -1, imagePartSize)
                                .contentType(image.getContentType())
                                .build()
                );

                Gallery galleryItem = Gallery.builder()
                        .serviceProviderId(gallery.getServiceProviderId())
                        .description(gallery.getDescription())
                        .imageUrl(objectName)
                        .contentType(image.getContentType())
                        .build();
                imageList.add(galleryItem);

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
        return Optional.of(galleryRepository.saveAll(imageList));
    }


    @Override
    public Optional<Gallery> updateGallery(Gallery gallery, MultipartFile image, Long id) {
        if (!galleryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gallery with such ID is not found");
        } else if (!image.isEmpty() && !(image.getContentType().equals("image/jpeg") || image.getContentType().equals("image/png"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image file is not jpeg/png");
        }
        gallery.setGalleryId(id);

        if (!image.isEmpty()) {
            try {
                String objectName = galleryRepository.getReferenceById(id).getImageUrl();
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket("images")
                                .object(objectName)
                                .stream(image.getInputStream(), -1, imagePartSize)
                                .contentType(image.getContentType())
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

        return Optional.of(galleryRepository.save(gallery));
    }


    @Override
    public void deleteGallery(Long id) {
        if (!galleryRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gallery with such ID is not found");

        try {
            String objectName = galleryRepository.getReferenceById(id).getImageUrl();
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket("images")
                            .object(objectName)
                            .build()
            );
        } catch (ErrorResponseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service error: " + e.getMessage());
        } catch (InsufficientDataException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Insufficient data available in the image object inputstream");
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
        galleryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteGalleryById(Long serviceProviderId) {
        if (!galleryRepository.existsByServiceProviderId(serviceProviderId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gallery with such service provider ID is not found");

        LinkedList<DeleteObject> deleteObjects = new LinkedList<>();
        galleryRepository.findAllByServiceProviderId(serviceProviderId).forEach(gallery -> {
            deleteObjects.add(new DeleteObject(gallery.getImageUrl()));
        });

        minioClient.removeObjects(
                RemoveObjectsArgs.builder()
                        .bucket("images")
                        .objects(deleteObjects)
                        .build()
        ).forEach(
                deleteErrorResult -> {
                    try {
                        deleteErrorResult.get();
                    } catch (ErrorResponseException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "S3 service error: " + e.getMessage());
                    } catch (InsufficientDataException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Insufficient data available in the image object inputstream");
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

        galleryRepository.deleteAllByServiceProviderId(serviceProviderId);
    }
}
