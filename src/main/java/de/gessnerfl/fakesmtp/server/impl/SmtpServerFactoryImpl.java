package de.gessnerfl.fakesmtp.server.impl;

import de.gessnerfl.fakesmtp.server.SmtpServer;
import de.gessnerfl.fakesmtp.server.SmtpServerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

@Service
@Profile("default")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SmtpServerFactoryImpl implements SmtpServerFactory {

  EmailPersister emailPersister;
  SmtpServerConfigurator configurator;

  @Override
  public SmtpServer create() {
    var simpleMessageListenerAdapter = new SimpleMessageListenerAdapter(emailPersister);
    var smtpServer = new SMTPServer(simpleMessageListenerAdapter);
    configurator.configure(smtpServer);
    return new SmtpServerImpl(smtpServer);
  }
}
