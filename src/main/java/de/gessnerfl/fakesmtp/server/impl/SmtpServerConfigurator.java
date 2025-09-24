package de.gessnerfl.fakesmtp.server.impl;

import de.gessnerfl.fakesmtp.config.FakeSmtpConfigurationProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.subethamail.smtp.auth.EasyAuthenticationHandlerFactory;
import org.subethamail.smtp.server.SMTPServer;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SmtpServerConfigurator {

  FakeSmtpConfigurationProperties fakeSmtpConfigurationProperties;
  BasicUsernamePasswordValidator basicUsernamePasswordValidator;
  Logger logger;

  public void configure(SMTPServer smtpServer) {
    smtpServer.setPort(fakeSmtpConfigurationProperties.getPort());
    smtpServer.setBindAddress(fakeSmtpConfigurationProperties.getBindAddress());
    if (fakeSmtpConfigurationProperties.getAuthentication() != null) {
      configureAuthentication(smtpServer, fakeSmtpConfigurationProperties.getAuthentication());
    }
  }

  private void configureAuthentication(
      SMTPServer smtpServer,
      FakeSmtpConfigurationProperties.Authentication authentication) {
    if (!StringUtils.hasText(authentication.getUsername())) {
      logger.error("Username is missing; skip configuration of authentication");
    } else if (!StringUtils.hasText(authentication.getPassword())) {
      logger.error("Password is missing; skip configuration of authentication");
    } else {
      logger.info("Setup simple username and password authentication for SMTP server");
      smtpServer.setAuthenticationHandlerFactory(new EasyAuthenticationHandlerFactory(
          basicUsernamePasswordValidator));
    }
  }
}
