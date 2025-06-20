package com.bhngupta.vanish.controller;

import com.bhngupta.vanish.model.EmailMessage;
import com.bhngupta.vanish.storage.TempEmailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class InboxController {

    private final TempEmailService emailService;

    public InboxController(TempEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/generate")
    public Map<String, String> generateTempEmail() {
        String address = emailService.generateTempEmail();
        return Map.of("email", address);
    }

    @GetMapping("/inbox/{id}")
    public List<EmailMessage> getInbox(@PathVariable String id) {
        return emailService.getInbox(id);
    }
}
