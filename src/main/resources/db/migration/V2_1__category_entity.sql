CREATE TABLE category (
    category_id INTEGER         AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255)    NOT NULL,
    label       VARCHAR(255)    NOT NULL,
    forward_to  VARCHAR(255)    NOT NULL,
    CONSTRAINT UK_category_title UNIQUE(title),
    CONSTRAINT UK_category_label UNIQUE(label)
);

