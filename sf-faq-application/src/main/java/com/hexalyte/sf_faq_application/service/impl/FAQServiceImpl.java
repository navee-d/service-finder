package com.hexalyte.sf_faq_application.service.impl;

import com.hexalyte.sf_faq_application.model.FAQ;
import com.hexalyte.sf_faq_application.repository.FAQRepository;
import com.hexalyte.sf_faq_application.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FAQServiceImpl implements FAQService {

    @Autowired
    private FAQRepository faqRepository;

    @Override
    public FAQ createFAQ(FAQ faq) {
        return faqRepository.save(faq);
    }

    @Override
    public Optional<FAQ> getFAQById(Integer id) {
        return faqRepository.findById(id);
    }

    @Override
    public List<FAQ> getAllFAQs() {
        return faqRepository.findAll();
    }

    @Override
    public Optional<FAQ> updateFAQ(Integer id, FAQ faqDetails) {
        return faqRepository.findById(id).map(existingFAQ -> {
            existingFAQ.setQuestion(faqDetails.getQuestion());
            existingFAQ.setAnswer(faqDetails.getAnswer());
            return faqRepository.save(existingFAQ);
        });
    }

    @Override
    public void deleteFAQ(Integer id) {
        if (!faqRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ not found");
        }
        faqRepository.deleteById(id);
    }
}