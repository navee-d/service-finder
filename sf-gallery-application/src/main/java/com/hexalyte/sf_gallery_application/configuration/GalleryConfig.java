package com.hexalyte.sf_gallery_application.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;

@ConfigurationProperties(prefix = "object-storage")
public class GalleryConfig {

    private final String endpoint;
    private final String accessKey;
    private final String secretKey;
    private final Map<String, String> buckets;

    public GalleryConfig(String endpoint, String accessKey, String secretKey, Map<String, String> buckets) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.buckets = buckets;
    }

    public String getEndpoint() { return endpoint; }
    public String getAccessKey() { return accessKey; }
    public String getSecretKey() { return secretKey; }
    public Map<String, String> getBuckets() { return buckets; }
}
