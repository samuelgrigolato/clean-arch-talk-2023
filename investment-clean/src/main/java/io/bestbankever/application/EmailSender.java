package io.bestbankever.application;

interface EmailSender {
    void sendEmail(EmailMessage message, String toAddress);
}
