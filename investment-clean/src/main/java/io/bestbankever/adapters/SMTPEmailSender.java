package io.bestbankever.adapters;

import io.bestbankever.application.EmailMessage;
import io.bestbankever.application.EmailSender;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
class SMTPEmailSender implements EmailSender {
    @Override
    public void sendEmail(EmailMessage emailMessage, String toAddress) {
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.host", System.getenv("SMTP_HOST"));
            prop.put("mail.smtp.port", "1025");

            Session session = Session.getInstance(prop);
            Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("investments@bestbankever.io"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(emailMessage.subject());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(emailMessage.htmlContent(), "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
