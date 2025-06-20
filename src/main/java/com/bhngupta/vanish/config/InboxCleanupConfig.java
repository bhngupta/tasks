package com.bhngupta.vanish.config;

import com.bhngupta.vanish.storage.TempEmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InboxCleanupConfig {

    private final TempEmailService emailService;

    public InboxCleanupConfig(TempEmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60_000) // Every 1 minute ??
    public void cleanUpExpiredInboxes() {
        emailService.cleanupExpired();
    }
}
