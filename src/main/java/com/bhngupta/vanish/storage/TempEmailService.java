package com.bhngupta.vanish.storage;

import com.bhngupta.vanish.model.EmailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
public class TempEmailService {

    private final Map<String, TempInbox> inboxes = new ConcurrentHashMap<>();
    private final long TTL_MINUTES = 10;

    public String generateTempEmail() {
        String id = UUID.randomUUID().toString().substring(0, 5);
        String address = id + "@mail.yourdomain.com";
        inboxes.put(id, new TempInbox(address, LocalDateTime.now()));
        return address;
    }

    public void storeEmail(String recipient, EmailMessage email) {
        String id = recipient.split("@")[0];
        TempInbox inbox = inboxes.get(id);
        if (inbox != null && !inbox.isExpired(TTL_MINUTES)) {
            inbox.getEmails().add(email);
        }
    }

    public List<EmailMessage> getInbox(String id) {
        TempInbox inbox = inboxes.get(id);
        if (inbox != null && !inbox.isExpired(TTL_MINUTES)) {
            return inbox.getEmails();
        }
        return Collections.emptyList();
    }

    public void cleanupExpired() {
        inboxes.entrySet().removeIf(entry -> entry.getValue().isExpired(TTL_MINUTES));
    }

    private static class TempInbox {
        private final String address;
        private final LocalDateTime createdAt;
        private final List<EmailMessage> emails = new CopyOnWriteArrayList<>();

        public TempInbox(String address, LocalDateTime createdAt) {
            this.address = address;
            this.createdAt = createdAt;
        }

        public boolean isExpired(long ttlMinutes) {
            return createdAt.plusMinutes(ttlMinutes).isBefore(LocalDateTime.now());
        }

        public List<EmailMessage> getEmails() {
            return emails;
        }
    }
}
