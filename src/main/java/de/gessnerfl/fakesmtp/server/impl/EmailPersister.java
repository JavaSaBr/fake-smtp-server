package de.gessnerfl.fakesmtp.server.impl;

import de.gessnerfl.fakesmtp.repository.EmailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.subethamail.smtp.helper.SimpleMessageListener;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = IOException.class)
public class EmailPersister implements SimpleMessageListener {

  EmailFactory emailFactory;
  EmailRepository emailRepository;
  Logger logger;

  @Override
  public boolean accept(String from, String recipient) {
    return true;
  }

  @Override
  public void deliver(String sender, String recipient, InputStream data) throws IOException {
    logger.info("Received email from {} for {}", sender, recipient);
    var rawData = new RawData(sender, recipient, IOUtils.toByteArray(data));
    var email = emailFactory.convert(rawData);
    emailRepository.save(email);
  }
}
