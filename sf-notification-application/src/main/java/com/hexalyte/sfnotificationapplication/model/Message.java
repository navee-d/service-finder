package com.hexalyte.sfnotificationapplication.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class Message {
    @NotBlank(message = "Subject cannot be empty")
    private String subject;

    private MultipartFile content;

    private MessageType type;

    @NotEmpty(message = "Must contain at least one recipient")
    private List<@Pattern(regexp = "^[a-z0-9_+&*-]+@(?:[a-z0-9]+\\.)+[a-z]+$", message = "Must contain valid Recipients") String> recipients;

    private List<MultipartFile> files;
}
