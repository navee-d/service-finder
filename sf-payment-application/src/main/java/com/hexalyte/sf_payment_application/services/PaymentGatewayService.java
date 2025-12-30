package com.hexalyte.sf_payment_application.service;

import com.hexalyte.sf_payment_application.model.PaymentGateway;
import java.util.List;

public interface PaymentGatewayService {
    PaymentGateway addGateway(PaymentGateway gateway);
    PaymentGateway getGatewayById(Integer gatewayId);
    PaymentGateway getGatewayByName(String name);
    List<PaymentGateway> getAllGateways();
    void deleteGateway(Integer gatewayId);
}