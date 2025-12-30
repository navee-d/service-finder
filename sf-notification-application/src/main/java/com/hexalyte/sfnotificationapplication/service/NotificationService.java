package com.hexalyte.sfnotificationapplication.service;

import com.hexalyte.sfnotificationapplication.model.Message;

public interface NotificationService {
    void sendSingleRecipient(Message message);
    void sendMultipleRecipients(Message message);
}
