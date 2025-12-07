
package com.hexalyte.sfnotificationapplication.service;

import com.hexalyte.sfnotificationapplication.model.SmsGateway;
import java.util.List;
import java.util.Optional;

public interface SmsGatewayService {
    SmsGateway createGateway(SmsGateway smsGateway);
    Optional<SmsGateway> getGatewayById(Integer gatewayId);
    List<SmsGateway> getAllGateways();
    SmsGateway updateGateway(Integer gatewayId, SmsGateway newDetails);
    void deleteGateway(Integer gatewayId);
}