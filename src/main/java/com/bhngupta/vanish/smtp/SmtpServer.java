package com.example.vanish.smtp;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.server.SMTPServer;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class SmtpServer {

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
                String rawEmail = buffer.toString(StandardCharsets.UTF_8);
                System.out.println("EMAIL RECEIVED for " + recipient + ":\n" + rawEmail);
                // Later: parse with Mime4j
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
