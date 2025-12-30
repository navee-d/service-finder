package com.hexalyte.sfnotificationapplication.controller;

import com.hexalyte.sfnotificationapplication.model.Message;
import com.hexalyte.sfnotificationapplication.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "notifications")
public class MessageController {

    private final NotificationService service;

    public MessageController(NotificationService service) {
        this.service = service;
    }

    @PostMapping(value = "single-recipient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private void sendSingleRecipient(@ModelAttribute @Valid Message message, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.getFieldError().getField().equals("type"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Message type should either be email or sms");
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
        }
        service.sendSingleRecipient(message);
    }

    @PostMapping(value = "multiple-recipients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private void sendMultipleRecipients(@ModelAttribute @Valid Message message,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.getFieldError().getField().equals("type"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Message type should either be email or sms");
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
        }
        service.sendMultipleRecipients(message);
    }

}
