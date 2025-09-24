package de.gessnerfl.fakesmtp.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.*;
import lombok.Getter;
import lombok.Setter;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Entity
@Getter
@Setter
@Table(name = "email")
public class Email {

  @Id
  @SequenceGenerator(
      name = "email_generator",
      sequenceName = "email_sequence",
      allocationSize = 1)
  @GeneratedValue(generator = "email_generator")
  private Long id;

  @Column(name = "from_address", nullable = false)
  @Basic(optional = false)
  private String fromAddress;

  @Column(name = "to_address", nullable = false)
  @Basic(optional = false)
  private String toAddress;

  @Lob
  @Column(name = "subject", nullable = false)
  @Basic(optional = false)
  private String subject;

  @Column(name = "received_on", nullable = false)
  @Basic(optional = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date receivedOn;

  @Lob
  @Column(name = "raw_data", nullable = false)
  @Basic(optional = false)
  private String rawData;

  @OneToMany(mappedBy = "email", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EmailContent> contents = new ArrayList<>();

  @OneToMany(mappedBy = "email", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EmailAttachment> attachments = new ArrayList<>();

  public void addContent(EmailContent content) {
    content.setEmail(this);
    contents.add(content);
  }

  public List<EmailContent> getContents() {
    return contents
        .stream()
        .sorted(comparing(EmailContent::getContentType))
        .collect(toList());
  }

  public Optional<EmailContent> getPlainContent() {
    return getContent(ContentType.PLAIN);
  }

  public Optional<EmailContent> getHtmlContent() {
    return getContent(ContentType.HTML);
  }

  private Optional<EmailContent> getContent(ContentType contentType) {
    return contents
        .stream()
        .filter(c -> contentType.equals(c.getContentType()))
        .findFirst();
  }

  public void addAttachment(EmailAttachment attachment) {
    attachment.setEmail(this);
    attachments.add(attachment);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    var email = (Email) o;

    return id.equals(email.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
