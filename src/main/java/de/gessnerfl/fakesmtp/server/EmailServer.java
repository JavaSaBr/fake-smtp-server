package de.gessnerfl.fakesmtp.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class EmailServer {

  final SmtpServerFactory smtpServerFactory;

  SmtpServer smtpServer;

  @PostConstruct
  public void startServer() {
    smtpServer = smtpServerFactory.create();
    smtpServer.start();
  }

  @PreDestroy
  public void shutdown() {
    if (smtpServer != null) {
      smtpServer.stop();
    }
  }
}
