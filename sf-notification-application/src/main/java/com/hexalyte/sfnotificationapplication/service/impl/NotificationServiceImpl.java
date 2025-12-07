package com.hexalyte.sfnotificationapplication.service.impl;

import com.hexalyte.sfnotificationapplication.model.Message;
import com.hexalyte.sfnotificationapplication.model.MessageType;
import com.hexalyte.sfnotificationapplication.service.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;
    private final String from;

    public NotificationServiceImpl(JavaMailSender mailSender, @Value("${spring.mail.username}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    @Override
    public void sendSingleRecipient(Message message) {

        if (message.getType().equals(MessageType.email)) {

            MultipartFile messageBody = message.getContent();
            if (messageBody.isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message body cannot be empty");
            else if (!(messageBody.getContentType().equals(MediaType.TEXT_HTML_VALUE) || messageBody.getContentType().equals(MediaType.TEXT_PLAIN_VALUE)))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message body must be plain text or html");

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setFrom(from);
                helper.setTo(message.getRecipients().get(0));
                helper.setSubject(message.getSubject());
                helper.setText(new String(messageBody.getBytes()), messageBody.getContentType().equals(MediaType.TEXT_HTML_VALUE));

                // ⭐ FIX: Check if files is not null before iterating
                if (message.getFiles() != null) {
                    for (MultipartFile file : message.getFiles()) {
                        if (!file.isEmpty())
                            helper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(file.getBytes()), file.getContentType());
                    }
                }
            } catch (MessagingException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid recipient, subject or content");
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get the message body content");
            }

            mailSender.send(mimeMessage);
        }
    }

    @Override
    public void sendMultipleRecipients(Message message) {

        if (message.getType().equals(MessageType.email)) {

            MultipartFile messageBody = message.getContent();
            if (messageBody.isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message body cannot be empty");
            else if (!(messageBody.getContentType().equals(MediaType.TEXT_HTML_VALUE) || messageBody.getContentType().equals(MediaType.TEXT_PLAIN_VALUE)))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message body must be plain text or html");

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setFrom(from);
                helper.setTo(message.getRecipients().toArray(new String[0]));
                helper.setSubject(message.getSubject());
                helper.setText(new String(messageBody.getBytes()), messageBody.getContentType().equals(MediaType.TEXT_HTML_VALUE));

                // ⭐ FIX: Check if files is not null before iterating
                if (message.getFiles() != null) {
                    for (MultipartFile file : message.getFiles()) {
                        if (!file.isEmpty())
                            helper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(file.getBytes()), file.getContentType());
                    }
                }
            } catch (MessagingException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid recipient, subject or content");
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get the message body content");
            }

            mailSender.send(mimeMessage);
        }
    }
}