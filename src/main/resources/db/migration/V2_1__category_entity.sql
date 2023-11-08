CREATE TABLE category (
    category_id INTEGER         AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255)    NOT NULL,
    label       VARCHAR(255)    NOT NULL,
    forward_to  VARCHAR(255)    NOT NULL,
    subject     VARCHAR(255)    NOT NULL,
    CONSTRAINT UK_category_title UNIQUE(title),
    CONSTRAINT UK_category_label UNIQUE(label)
);
ALTER TABLE IF EXISTS incident
    MODIFY category_id INTEGER NOT NULL;

ALTER TABLE IF EXISTS incident
    ADD CONSTRAINT FK_category
    FOREIGN KEY (category_id) REFERENCES category(category_id);

CREATE TABLE incident_attachments (
    incident_id VARCHAR(36) NOT NULL,
    attachment_id INTEGER NOT NULL
);

ALTER TABLE IF EXISTS attachment
    DROP COLUMN incident_id;

