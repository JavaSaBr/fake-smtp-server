package de.gessnerfl.fakesmtp.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_attachment")
public class EmailAttachment {
    @Id
    @SequenceGenerator(name = "email_attachment_generator", sequenceName = "email_attachment_sequence", allocationSize = 1)
    @GeneratedValue(generator = "email_attachment_generator")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY, optional = false)
    @JoinColumn(name="email")
    private Email email;

    @Column(name="filename", nullable = false, length = 1024)
    @Basic(optional = false)
    private String filename;

    @Lob
    @Column(name="data", nullable = false)
    @Basic(optional = false)
    private byte[] data;

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
