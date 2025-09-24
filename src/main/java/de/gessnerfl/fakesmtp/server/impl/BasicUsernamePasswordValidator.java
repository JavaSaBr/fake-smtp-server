package de.gessnerfl.fakesmtp.server.impl;

import de.gessnerfl.fakesmtp.config.FakeSmtpConfigurationProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subethamail.smtp.auth.LoginFailedException;
import org.subethamail.smtp.auth.UsernamePasswordValidator;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BasicUsernamePasswordValidator implements UsernamePasswordValidator {

  FakeSmtpConfigurationProperties fakeSmtpConfigurationProperties;

  @Override
  public void login(String username, String password) throws LoginFailedException {
    if (!isUsernameValid(username) || !isPasswordValid(password)) {
      throw new LoginFailedException("Invalid Username or Password");
    }
  }

  private boolean isUsernameValid(String username) {
    return getAuthentication()
        .getUsername()
        .equals(username);
  }

  private boolean isPasswordValid(String password) {
    return getAuthentication()
        .getPassword()
        .equals(password);
  }

  private FakeSmtpConfigurationProperties.Authentication getAuthentication() {
    return fakeSmtpConfigurationProperties.getAuthentication();
  }
}
