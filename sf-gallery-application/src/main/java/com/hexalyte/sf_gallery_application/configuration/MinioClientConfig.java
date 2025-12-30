package com.hexalyte.sf_gallery_application.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;
import java.util.Map;
@Configuration
public class MinioClientConfig {

    private final GalleryConfig galleryConfig;

    public MinioClientConfig(GalleryConfig galleryConfig) {
        this.galleryConfig = galleryConfig;
    }

    @Bean
    public MinioClient minioClient() throws Exception {
        MinioClient client = MinioClient.builder()
                .endpoint(galleryConfig.getEndpoint())
                .credentials(galleryConfig.getAccessKey(), galleryConfig.getSecretKey())
                .build();

        Map<String, String> buckets = galleryConfig.getBuckets();
        if (buckets != null) {
            for (Map.Entry<String, String> entry : buckets.entrySet()) {
                String bucketName = entry.getKey();
                String policyPath = entry.getValue();

                boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
                if (!exists) {
                    client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                    System.out.println("✅ Created bucket: " + bucketName);

                    if (policyPath != null && !policyPath.isEmpty()) {
                        ClassPathResource resource = new ClassPathResource(policyPath.replace("classpath:", ""));
                        String policyJson = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

                        client.setBucketPolicy(
                                SetBucketPolicyArgs.builder()
                                        .bucket(bucketName)
                                        .config(policyJson)
                                        .build()
                        );
                        System.out.println("✅ Policy applied for: " + bucketName);
                    }
                } else {
                    System.out.println("ℹ️ Bucket already exists: " + bucketName);
                }
            }
        }

        return client;
    }
}
