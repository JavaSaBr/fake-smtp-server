package de.gessnerfl.fakesmtp.service;

import de.gessnerfl.fakesmtp.config.FakeSmtpConfigurationProperties;
import de.gessnerfl.fakesmtp.repository.EmailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailRetentionTimer {

  FakeSmtpConfigurationProperties fakeSmtpConfigurationProperties;
  EmailRepository emailRepository;
  Logger logger;

  @Scheduled(fixedDelay = 300000, initialDelay = 500)
  public void deleteOutdatedMails() {
    var persistence = fakeSmtpConfigurationProperties.getPersistence();
    if (isDataRetentionConfigured(persistence)) {
      var maxNumber = persistence.getMaxNumberEmails();
      var count = emailRepository.deleteEmailsExceedingDateRetentionLimit(maxNumber);
      if (count != 0) {
        logger.info("Deleted {} emails which exceeded the maximum number {} of emails to be stored", count, maxNumber);
      }
    }
  }

  private boolean isDataRetentionConfigured(FakeSmtpConfigurationProperties.Persistence persistence) {
    return persistence != null && persistence.getMaxNumberEmails() != null && persistence.getMaxNumberEmails() > 0;
  }
}
