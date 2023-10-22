package io.bestbankever.application;

public interface EmailSender {
    void sendEmail(EmailMessage message, String toAddress);
}
