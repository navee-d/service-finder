package com.hexalyte.sf_faq_application.service;

import com.hexalyte.sf_faq_application.model.FAQ;
import java.util.List;
import java.util.Optional;

public interface FAQService {
    FAQ createFAQ(FAQ faq);
    Optional<FAQ> getFAQById(Integer id);
    List<FAQ> getAllFAQs();
    Optional<FAQ> updateFAQ(Integer id, FAQ faqDetails);
    void deleteFAQ(Integer id);
}