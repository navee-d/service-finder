package com.hexalyte.sf_payment_application.service;

import com.hexalyte.sf_payment_application.model.PaymentGateway;
import com.hexalyte.sf_payment_application.repository.PaymentGatewayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    @Autowired
    private PaymentGatewayRepository paymentGatewayRepository;

    @Override
    public PaymentGateway addGateway(PaymentGateway gateway) {
        return paymentGatewayRepository.save(gateway);
    }

    @Override
    public PaymentGateway getGatewayById(Integer gatewayId) {
        return paymentGatewayRepository.findById(gatewayId)
                .orElseThrow(() -> new RuntimeException("Gateway not found with id: " + gatewayId));
    }

    @Override
    public PaymentGateway getGatewayByName(String name) {
        return paymentGatewayRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Gateway not found with name: " + name));
    }

    @Override
    public List<PaymentGateway> getAllGateways() {
        return paymentGatewayRepository.findAll();
    }

    @Override
    public void deleteGateway(Integer gatewayId) {
        if (!paymentGatewayRepository.existsById(gatewayId)) {
            throw new RuntimeException("Gateway not found with id: " + gatewayId);
        }
        paymentGatewayRepository.deleteById(gatewayId);
    }
}