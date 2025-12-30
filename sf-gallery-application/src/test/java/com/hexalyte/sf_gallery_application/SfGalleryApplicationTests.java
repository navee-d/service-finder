package com.hexalyte.sf_gallery_application;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class SfGalleryApplicationTests {

    @Bean
    public MinioClient minioClient() {
        // Return a dummy MinioClient or mock
        return MinioClient.builder()
                .endpoint("http://127.0.0.1:9000") // not actually used
                .credentials("minioadmin", "minioadmin")
                .build();
    }
}
