package de.gessnerfl.fakesmtp.server.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SmtpServerFactoryImplTest {
  private final int PORT = 25;

  @Mock
  private SmtpServerConfigurator configurator;
  @Mock
  private EmailPersister emailPersister;

  @InjectMocks
  private SmtpServerFactoryImpl sut;

  @Test
  public void shouldCreateAndConfigureNewInsance() {
    var smtpServer = sut.create();

    Assertions.assertInstanceOf(SmtpServerImpl.class, smtpServer);
    var impl = (SmtpServerImpl) smtpServer;
    assertNotNull(impl.smtpServer);

    verify(configurator).configure(impl.smtpServer);
  }

}