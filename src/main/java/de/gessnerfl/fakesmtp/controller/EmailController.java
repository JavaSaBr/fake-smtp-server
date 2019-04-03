package de.gessnerfl.fakesmtp.controller;

import de.gessnerfl.fakesmtp.repository.EmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
public class EmailController {

    private final EmailRepository emailRepository;

    @GetMapping("/count/email/from/{from}")
    long getEmailsCountFrom(@PathVariable String from) {
        return emailRepository.countByFromAddress(from);
    }

    @DeleteMapping("/emails")
    void deleteAll() {
        emailRepository.deleteAll();
        emailRepository.flush();
    }
}
