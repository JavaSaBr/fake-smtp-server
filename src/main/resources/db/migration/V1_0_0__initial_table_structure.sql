CREATE TABLE email (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    from_address VARCHAR(255) NOT NULL,
    to_address VARCHAR(255) NOT NULL,
    subject TEXT NOT NULL,
    received_on TIMESTAMP NOT NULL,
    raw_data TEXT NOT NULL
);

CREATE TABLE email_content (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    email BIGINT NOT NULL,
    content_type varchar(32) NOT NULL,
    data TEXT NOT NULL
);
ALTER TABLE email_content ADD FOREIGN KEY (email) REFERENCES email(id) ON DELETE CASCADE;

CREATE TABLE email_attachment (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    email BIGINT NOT NULL,
    filename varchar(1024) NOT NULL,
    data LONGBLOB NOT NULL
);
ALTER TABLE email_attachment ADD FOREIGN KEY (email) REFERENCES email(id) ON DELETE CASCADE;

CREATE SEQUENCE email_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE email_content_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE email_attachment_sequence START WITH 1 INCREMENT BY 1;