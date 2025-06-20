package com.bhngupta.vanish.smtp;

import com.bhngupta.vanish.model.EmailMessage;
import com.bhngupta.vanish.storage.TempEmailService;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.server.SMTPServer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class SmtpServer {

    private final TempEmailService tempEmailService;

    public SmtpServer(TempEmailService tempEmailService) {
        this.tempEmailService = tempEmailService;
    }

    @PostConstruct
    public void startSmtpServer() {
        MessageHandlerFactory factory = (ctx) -> new MessageHandler() {
            private String recipient;

            @Override
            public void from(String from) throws RejectException {
                System.out.println("MAIL FROM: " + from);
            }

            @Override
            public void recipient(String recipient) throws RejectException {
                System.out.println("RCPT TO: " + recipient);
                if (!recipient.endsWith("@mail.yourdomain.com")) { // TODO: make a new subdomain - mail.bhngupta.com ??
                    throw new RejectException(553, "Only accepts @mail.yourdomain.com");
                }
                this.recipient = recipient;
            }

            @Override
            public void data(InputStream data) throws IOException {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                data.transferTo(buffer);
                byte[] raw = buffer.toByteArray();

                DefaultMessageBuilder builder = new DefaultMessageBuilder();
                builder.setMimeEntityConfig(MimeConfig.DEFAULT);
                Message mimeMessage = builder.parseMessage(new ByteArrayInputStream(raw));

                String subject = mimeMessage.getSubject();
                String from = mimeMessage.getFrom().toString();
                String body = mimeMessage.getBody().toString(); // Simple text emails
                // Advanced: check type and parse multipart later

                EmailMessage email = new EmailMessage(
                    UUID.randomUUID().toString(),
                    this.recipient,
                    from,
                    subject,
                    body,
                    LocalDateTime.now(),
                    Collections.emptyList()
                );

                System.out.println("Parsed Email: " + email.getSubject() + " from " + email.getFrom());

                tempEmailService.storeEmail(this.recipient, email);
            }

            @Override
            public void done() {
                System.out.println("Done handling email");
            }
        };

        SMTPServer smtpServer = new SMTPServer(factory);
        smtpServer.setPort(2525);
        smtpServer.start();
        System.out.println("SMTP server started on port 2525");
    }
}
