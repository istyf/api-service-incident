create table attachment
(
                            id integer not null auto_increment,
                            created datetime(6),
                            category varchar(255),
                            extension varchar(255),
                            file LONGTEXT,
                            incident_id varchar(255),
                            mime_type varchar(255),
                            name varchar(255),
                            note varchar(255),
                            primary key (id)
) engine=InnoDB;

create table category
(
                          category_id integer not null auto_increment,
                          forward_to varchar(255),
                          label varchar(255),
                          subject varchar(255),
                          title varchar(255),
                          primary key (category_id)
) engine=InnoDB;

create table incident
(
                          category_id integer,
                          created datetime(6),
                          updated datetime(6),
                          contact_method varchar(255),
                          coordinates varchar(255),
                          description varchar(255),
                          email varchar(255),
                          external_case_id varchar(255),
                          feedback varchar(255),
                          id varchar(255) not null,
                          person_id varchar(255),
                          phone_number varchar(255),
                          status enum ('ARKIVERAD','ERROR','INSKICKAT','KLART','KOMPLETTERAD','SPARAT','UNDER_BEHANDLING','VANTAR_KOMPLETTERING'),
                          primary key (id)
) engine=InnoDB;

alter table if exists category
    add constraint UK_category_title unique (title);

alter table if exists category
    add constraint UK_category_label unique (label);

alter table if exists attachment
    add constraint fk_incident_attachment_incident_id
    foreign key (incident_id)
    references incident (id);

alter table if exists incident
    add constraint fk_incident_category
    foreign key (category_id)
    references category (category_id);
