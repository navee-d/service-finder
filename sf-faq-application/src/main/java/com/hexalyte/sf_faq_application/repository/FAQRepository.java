package com.hexalyte.sf_faq_application.repository;

import com.hexalyte.sf_faq_application.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Integer> {
}