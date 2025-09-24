package de.gessnerfl.fakesmtp.server.impl;

import de.gessnerfl.fakesmtp.config.FakeSmtpConfigurationProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.subethamail.smtp.auth.LoginFailedException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasicUsernamePasswordValidatorTest {

  @Mock
  private FakeSmtpConfigurationProperties fakeSmtpConfigurationProperties;

  @InjectMocks
  private BasicUsernamePasswordValidator sut;

  @Test
  public void shouldSuccessfullyValidateCorrectUsernameAndPassword() throws Exception {
    var username = "username";
    var password = "password";
    var authentication = mock(FakeSmtpConfigurationProperties.Authentication.class);
    when(authentication.getUsername()).thenReturn(username);
    when(authentication.getPassword()).thenReturn(password);
    when(fakeSmtpConfigurationProperties.getAuthentication()).thenReturn(authentication);

    sut.login(username, password);

    verify(authentication).getUsername();
    verify(authentication).getPassword();
  }

  @Test
  public void shouldThrowLoginFailedExceptionWhenUsernameIsNotValid() {
    var username = "username";
    var invalidUsername = "inValidUsername";
    var password = "password";
    var authentication = mock(FakeSmtpConfigurationProperties.Authentication.class);
    when(authentication.getUsername()).thenReturn(username);
    when(fakeSmtpConfigurationProperties.getAuthentication()).thenReturn(authentication);

    Assertions.assertThrows(LoginFailedException.class, () -> sut.login(invalidUsername, password));
  }

  @Test
  public void shouldThrowLoginFailedExceptionWhenPasswordIsNotValid() {
    var username = "username";
    var password = "password";
    var invalidPassword = "invalidPassword";
    var authentication = mock(FakeSmtpConfigurationProperties.Authentication.class);
    when(authentication.getUsername()).thenReturn(username);
    when(authentication.getPassword()).thenReturn(password);
    when(fakeSmtpConfigurationProperties.getAuthentication()).thenReturn(authentication);

    Assertions.assertThrows(LoginFailedException.class, () -> sut.login(username, invalidPassword));
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenAuthenticationIsMissing() {
    var username = "username";
    var password = "password";
    when(fakeSmtpConfigurationProperties.getAuthentication()).thenReturn(null);

    Assertions.assertThrows(NullPointerException.class, () -> sut.login(username, password));
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenUsernameIsMissingInAuthentication() {
    var username = "username";
    var password = "password";
    var authentication = mock(FakeSmtpConfigurationProperties.Authentication.class);
    when(authentication.getUsername()).thenReturn(null);
    when(fakeSmtpConfigurationProperties.getAuthentication()).thenReturn(authentication);

    Assertions.assertThrows(NullPointerException.class, () -> sut.login(username, password));
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenPasswordIsMissingInAuthentication() {
    var username = "username";
    var password = "password";
    var authentication = mock(FakeSmtpConfigurationProperties.Authentication.class);
    when(authentication.getUsername()).thenReturn(username);
    when(authentication.getPassword()).thenReturn(null);
    when(fakeSmtpConfigurationProperties.getAuthentication()).thenReturn(authentication);

    Assertions.assertThrows(NullPointerException.class, () -> sut.login(username, password));
  }

}