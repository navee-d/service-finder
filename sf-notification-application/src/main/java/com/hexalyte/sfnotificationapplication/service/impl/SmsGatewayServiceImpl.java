package com.hexalyte.sfnotificationapplication.service.impl;

import com.hexalyte.sfnotificationapplication.model.SmsGateway;
import com.hexalyte.sfnotificationapplication.repository.SmsGatewayRepository;
import com.hexalyte.sfnotificationapplication.service.SmsGatewayService;
import jakarta.persistence.EntityNotFoundException; // Using a better exception
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SmsGatewayServiceImpl implements SmsGatewayService {

    @Autowired
    private SmsGatewayRepository smsGatewayRepository;

    @Override
    public SmsGateway createGateway(SmsGateway smsGateway) {
        smsGateway.setGatewayId(null);
        return smsGatewayRepository.save(smsGateway);
    }

    @Override
    public Optional<SmsGateway> getGatewayById(Integer gatewayId) {
        return smsGatewayRepository.findById(gatewayId);
    }

    @Override
    public List<SmsGateway> getAllGateways() {
        return smsGatewayRepository.findAll();
    }

    @Override
    public SmsGateway updateGateway(Integer gatewayId, SmsGateway newDetails) {
        SmsGateway existingGateway = smsGatewayRepository.findById(gatewayId)
                .orElseThrow(() -> new EntityNotFoundException("Gateway not found with id: " + gatewayId));

        existingGateway.setName(newDetails.getName());
        existingGateway.setDescription(newDetails.getDescription());
        existingGateway.setApiKey(newDetails.getApiKey());

        return smsGatewayRepository.save(existingGateway);
    }

    @Override
    public void deleteGateway(Integer gatewayId) {
        if (!smsGatewayRepository.existsById(gatewayId)) {
            throw new EntityNotFoundException("Gateway not found with id: " + gatewayId);
        }
        smsGatewayRepository.deleteById(gatewayId);
    }
}