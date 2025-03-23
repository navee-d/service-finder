package com.hexalyte.sf_gallery_application.configuration;

import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
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

@Configuration
@EnableJpaAuditing
public class GalleryConfig implements CommandLineRunner {

    @Getter(onMethod_ = {@Bean})
    private final MinioClient minioClient = MinioClient.builder().endpoint("http://127.0.0.1:9000").credentials("minioadmin", "minioadmin").build();

    @Override
    public void run(String... args) throws Exception {

        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("images").build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("images").build());
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket("images")
                            .config(FileUtils.readFileToString(new File("sf-gallery-application/src/main/resources/bucket_policy_config/bucketpolicy.json"), StandardCharsets.UTF_8))
                            .build()
            );
        } else {
            System.out.println("Bucket already exists");
        }

    }

    @EventListener(ContextClosedEvent.class)
    @Profile("development")
    public void deleteBucket() throws Exception {

        Iterable<Result<Item>> images = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket("images")
                        .build()
        );

        List<DeleteObject> delImages = new LinkedList<>();
        for (Result<Item> image : images) {
            delImages.add(new DeleteObject(image.get().objectName()));
        }

        Iterable<Result<DeleteError>> deletingImages = minioClient.removeObjects(
                RemoveObjectsArgs.builder()
                        .bucket("images")
                        .objects(delImages)
                        .build()
        );
        for (Result<DeleteError> image : deletingImages) {
            image.get();
        }

        minioClient.removeBucket(
                RemoveBucketArgs.builder()
                        .bucket("images")
                        .build()
        );

    }

}
