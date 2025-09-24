package de.gessnerfl.fakesmtp.server.impl;

import de.gessnerfl.fakesmtp.server.SmtpServer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.subethamail.smtp.server.SMTPServer;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class SmtpServerImpl implements SmtpServer {

  SMTPServer smtpServer;

  @Override
  public void start() {
    smtpServer.start();
  }

  @Override
  public void stop() {
    smtpServer.stop();
  }
}
