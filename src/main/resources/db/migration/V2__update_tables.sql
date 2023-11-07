DROP TABLE IF EXISTS Status;
DROP TABLE IF EXISTS Categories;

ALTER TABLE IF EXISTS Attachments_SEQ
    RENAME TO attachment_sequence;

ALTER TABLE IF EXISTS Attachments
    RENAME TO attachment;

ALTER TABLE IF EXISTS Errands
    RENAME TO incident;

ALTER TABLE IF EXISTS attachment
    RENAME COLUMN ID TO id;

ALTER TABLE IF EXISTS attachment
    RENAME COLUMN IncidentId TO incident_id;

ALTER TABLE IF EXISTS attachment
    RENAME COLUMN Created TO created;

ALTER TABLE IF EXISTS attachment
    RENAME COLUMN mimetype TO mime_type;

ALTER TABLE IF EXISTS attachment
    RENAME COLUMN Created TO created;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN externalCaseId TO external_case_id;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN PersonId TO person_id;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN Created TO created;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN PhoneNumber TO phone_number;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN Email TO email;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN ContactMethod TO contact_method;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN Updated TO updated;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN Description TO description;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN MapCoordinates TO coordinates;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN Feedback TO feedback;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN Status TO status;

ALTER TABLE IF EXISTS incident
    MODIFY person_id VARCHAR(36) NOT NULL;

ALTER TABLE IF EXISTS incident
    MODIFY created DATETIME NOT NULL;

ALTER TABLE IF EXISTS incident
    MODIFY updated DATETIME NOT NULL;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN Category TO category_id;

ALTER TABLE IF EXISTS incident
    MODIFY status ENUM('INSKICKAT', 'KLART', 'KOMPLETTERAD', 'SPARAT', 'UNDER_BEHANDLING',
    'VANTAR_KOMPLETTERING', 'ARKIVERAD', 'ERROR');

ALTER TABLE IF EXISTS attachment
    MODIFY created DATETIME NOT NULL;

ALTER TABLE IF EXISTS attachment
    MODIFY incident_id VARCHAR(36) NOT NULL;

ALTER TABLE IF EXISTS attachment
    MODIFY file LONGTEXT NOT NULL;


ALTER TABLE IF EXISTS incident
    DROP COLUMN ID;

ALTER TABLE IF EXISTS incident
    RENAME COLUMN IncidentId TO id;

ALTER TABLE IF EXISTS incident
    ADD PRIMARY KEY (id);






