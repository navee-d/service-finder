package com.hexalyte.sf_faq_application.controller;

import com.hexalyte.sf_faq_application.model.FAQ;
import com.hexalyte.sf_faq_application.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faqs")
public class FAQController {

    @Autowired
    private FAQService faqService;

    @PostMapping
    public ResponseEntity<FAQ> createFAQ(@RequestBody FAQ faq) {
        return ResponseEntity.ok(faqService.createFAQ(faq));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FAQ> getFAQById(@PathVariable Integer id) {
        return ResponseEntity.of(faqService.getFAQById(id));
    }

    @GetMapping
    public ResponseEntity<List<FAQ>> getAllFAQs() {
        return ResponseEntity.ok(faqService.getAllFAQs());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FAQ> updateFAQ(@PathVariable Integer id, @RequestBody FAQ faqDetails) {
        return ResponseEntity.of(faqService.updateFAQ(id, faqDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable Integer id) {
        faqService.deleteFAQ(id);
        return ResponseEntity.noContent().build();
    }
}