package com.hexalyte.sf_faq_application.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "faqs")
public class FAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAQID")
    private Integer faqId;

    @Column(name = "Question", nullable = false)
    private String question;

    @Column(name = "Answer", nullable = false, columnDefinition = "text")
    private String answer;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "UpdatedAt", nullable = false)
    private Timestamp updatedAt;
}