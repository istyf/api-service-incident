create table Attachments (
    ID         int auto_increment primary key,
    category   varchar(255) null,
    extension  varchar(255) null,
    mimetype   varchar(255) null,
    note       varchar(255) null,
    file       varchar(255) null,
    IncidentId varchar(255) null,
    name       varchar(255) not null,
    Created    varchar(255) null
);

create table Attachments_SEQ (
    next_not_cached_value bigint(21)          not null,
    minimum_value         bigint(21)          not null,
    maximum_value         bigint(21)          not null,
    start_value           bigint(21)          not null comment 'start value when sequences is created or value if RESTART is used',
    increment             bigint(21)          not null comment 'increment value',
    cache_size            bigint(21) unsigned not null,
    cycle_option          tinyint(1) unsigned not null comment '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
    cycle_count           bigint(21)          not null comment 'How many cycles have been done'
);

create table Categories (
    ID          int auto_increment primary key,
    CATEGORY_ID int          not null,
    Category    varchar(255) not null,
    ISYCaseId   int          null,
    constraint CATEGORY_ID unique (CATEGORY_ID)
);

create table Errands (
    ID             int auto_increment primary key,
    IncidentId     varchar(255) not null,
    PersonId       varchar(255) null,
    Created        varchar(255) null,
    Updated        varchar(255) null,
    PhoneNumber    varchar(255) null,
    Email          varchar(255) null,
    ContactMethod  varchar(255) null,
    Category       varchar(255) not null,
    Description    varchar(255) null,
    MapCoordinates varchar(255) null,
    Status         varchar(255) not null,
    Feedback       varchar(255) null,
    externalCaseId varchar(255) null
);

create table Status (
    ID        int auto_increment primary key,
    STATUS_ID int          not null,
    Status    varchar(255) null,
    constraint STATUS_ID unique (STATUS_ID)
);

create table hibernate_sequence (
    next_not_cached_value bigint(21)          not null,
    minimum_value         bigint(21)          not null,
    maximum_value         bigint(21)          not null,
    start_value           bigint(21)          not null comment 'start value when sequences is created or value if RESTART is used',
    increment             bigint(21)          not null comment 'increment value',
    cache_size            bigint(21) unsigned not null,
    cycle_option          tinyint(1) unsigned not null comment '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
    cycle_count           bigint(21)          not null comment 'How many cycles have been done'
);


