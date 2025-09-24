package de.gessnerfl.fakesmtp.controller;

import de.gessnerfl.fakesmtp.repository.EmailRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {

  EmailRepository emailRepository;

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
