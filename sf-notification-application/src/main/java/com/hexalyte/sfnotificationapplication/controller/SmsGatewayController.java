package com.hexalyte.sfnotificationapplication.controller;

import com.hexalyte.sfnotificationapplication.model.SmsGateway;
import com.hexalyte.sfnotificationapplication.service.SmsGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notifications/sms-gateways") // This path will be routed by the gateway
public class SmsGatewayController {

    @Autowired
    private SmsGatewayService smsGatewayService;

    @PostMapping
    public ResponseEntity<SmsGateway> createGateway(@RequestBody SmsGateway smsGateway) {
        SmsGateway createdGateway = smsGatewayService.createGateway(smsGateway);
        return ResponseEntity.ok(createdGateway);
    }

    @GetMapping
    public ResponseEntity<List<SmsGateway>> getAllGateways() {
        List<SmsGateway> gateways = smsGatewayService.getAllGateways();
        return ResponseEntity.ok(gateways);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SmsGateway> getGatewayById(@PathVariable Integer id) {
        return smsGatewayService.getGatewayById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SmsGateway> updateGateway(@PathVariable Integer id, @RequestBody SmsGateway newDetails) {
        try {
            return ResponseEntity.ok(smsGatewayService.updateGateway(id, newDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGateway(@PathVariable Integer id) {
        try {
            smsGatewayService.deleteGateway(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}