package com.hexalyte.sf_serviceprovider_application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Use test profile to disable Eureka
class SfServiceProviderApplicationTests {

    @Test
    void contextLoads() {
    }
}
