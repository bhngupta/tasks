package com.bhngupta.vanish.model;

import java.time.LocalDateTime;
import java.util.List;

public class EmailMessage {
    private String id; // Unique ID or email address
    private String to;
    private String from;
    private String subject;
    private String body;
    private LocalDateTime receivedAt;
    private List<String> attachments;

    // Getters, Setters, Constructors

    public EmailMessage(String id, String to, String from, String subject, String body, LocalDateTime receivedAt, List<String> attachments) {
        this.id = id;
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
        this.receivedAt = receivedAt;
        this.attachments = attachments;
    }

    // Getters and setters...
}
