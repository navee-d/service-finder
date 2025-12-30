package com.hexalyte.sf_payment_application.repository;

import com.hexalyte.sf_payment_application.model.PaymentGateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentGatewayRepository extends JpaRepository<PaymentGateway, Integer> {
    Optional<PaymentGateway> findByName(String name);
}