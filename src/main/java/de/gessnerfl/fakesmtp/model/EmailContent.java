package de.gessnerfl.fakesmtp.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_content")
public class EmailContent {
    @Id
    @SequenceGenerator(name = "email_content_generator", sequenceName = "email_content_sequence", allocationSize = 1)
    @GeneratedValue(generator = "email_content_generator")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY, optional = false)
    @JoinColumn(name="email")
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name="content_type", nullable = false)
    @Basic(optional = false)
    private ContentType contentType;

    @Lob
    @Column(name="data", nullable = false)
    @Basic(optional = false)
    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}
