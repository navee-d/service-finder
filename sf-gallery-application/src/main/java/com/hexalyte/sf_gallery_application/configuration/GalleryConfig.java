package com.hexalyte.sf_gallery_application.configuration;

import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@EnableJpaAuditing
@ConfigurationProperties(prefix = "object-storage")
public class GalleryConfig implements CommandLineRunner {

    @Getter(onMethod_ = {@Bean})
    private final MinioClient minioClient;
    private final Map<String, String> buckets;

    public GalleryConfig(final String endpoint, final Map<String, String> buckets) {
        this.minioClient = MinioClient.builder().endpoint(endpoint).credentials("minioadmin", "minioadmin").build();
        this.buckets = buckets;
    }

    @Override
    public void run(String... args) throws Exception {

        for (Map.Entry<String, String> bucketEntry : buckets.entrySet()) {
            String bucket = bucketEntry.getKey();
            String bucketPolicyFile = bucketEntry.getValue();

            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
                minioClient.setBucketPolicy(
                        SetBucketPolicyArgs.builder()
                                .bucket(bucket)
                                .config(FileUtils.readFileToString(new File(bucketPolicyFile), StandardCharsets.UTF_8))
                                .build()
                );
            } else {
                System.out.println(bucket + " bucket already exists");
            }
        }

    }

    @EventListener(ContextClosedEvent.class)
    @Profile("development")
    public void deleteBucket() throws Exception {

        for (Map.Entry<String, String> bucketEntry : buckets.entrySet()) {
            String bucket = bucketEntry.getKey();

            Iterable<Result<Item>> images = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucket)
                            .recursive(true)
                            .build()
            );

            List<DeleteObject> delImages = new LinkedList<>();
            for (Result<Item> image : images) {
                delImages.add(new DeleteObject(image.get().objectName()));
            }

            Iterable<Result<DeleteError>> deletingImages = minioClient.removeObjects(
                    RemoveObjectsArgs.builder()
                            .bucket(bucket)
                            .objects(delImages)
                            .build()
            );
            for (Result<DeleteError> image : deletingImages) {
                image.get();
            }

            minioClient.removeBucket(
                    RemoveBucketArgs.builder()
                            .bucket(bucket)
                            .build()
            );
        }
    }

}
