package de.gessnerfl.fakesmtp.server.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class RawData {

  @Getter
   String from;
  @Getter
  String to;

  byte[] content;

  public String getContentAsString() {
    return new String(content, StandardCharsets.UTF_8);
  }

  public InputStream getContentAsStream() {
    return new ByteArrayInputStream(content);
  }
}
