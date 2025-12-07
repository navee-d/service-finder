package com.hexalyte.sf_payment_application.controller;

import com.hexalyte.sf_payment_application.model.PaymentGateway;
import com.hexalyte.sf_payment_application.service.PaymentGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments/gateways")
public class PaymentGatewayController {

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @PostMapping
    public ResponseEntity<PaymentGateway> addGateway(@RequestBody PaymentGateway gateway) {
        PaymentGateway newGateway = paymentGatewayService.addGateway(gateway);
        return ResponseEntity.ok(newGateway);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentGateway> getGatewayById(@PathVariable Integer id) {
        PaymentGateway gateway = paymentGatewayService.getGatewayById(id);
        return ResponseEntity.ok(gateway);
    }

    @GetMapping
    public ResponseEntity<List<PaymentGateway>> getAllGateways() {
        List<PaymentGateway> gateways = paymentGatewayService.getAllGateways();
        return ResponseEntity.ok(gateways);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGateway(@PathVariable Integer id) {
        paymentGatewayService.deleteGateway(id);
        return ResponseEntity.noContent().build();
    }
}