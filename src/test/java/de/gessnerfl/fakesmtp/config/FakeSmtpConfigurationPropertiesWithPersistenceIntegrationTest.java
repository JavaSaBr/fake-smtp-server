package de.gessnerfl.fakesmtp.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.InetAddress;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({
    "integration-test",
    "config-with-persistence-integration-test"
})
public class FakeSmtpConfigurationPropertiesWithPersistenceIntegrationTest {

  @Autowired
  private FakeSmtpConfigurationProperties sut;

  @Test
  public void shouldLoadConfigurationParameters() throws Exception {
    assertEquals(1234,
        sut
            .getPort()
            .intValue());
    assertEquals(InetAddress.getByName("127.0.0.1"), sut.getBindAddress());
    assertNull(sut.getAuthentication());
    assertNotNull(sut.getPersistence());
    assertEquals(5,
        sut
            .getPersistence()
            .getMaxNumberEmails()
            .intValue());
  }
}