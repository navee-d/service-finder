package com.hexalyte.sfnotificationapplication.repository;

import com.hexalyte.sfnotificationapplication.model.SmsGateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsGatewayRepository extends JpaRepository<SmsGateway, Integer> {
}