package com.hexalyte.sf_serviceprovider_application.repository;

import com.hexalyte.sf_serviceprovider_application.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider,Long> {
}
