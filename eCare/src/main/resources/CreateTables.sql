CREATE
DATABASE  eCare_db;
USE
eCare_db;

CREATE TABLE options
(
    id              int NOT NULL AUTO_INCREMENT,
    name            varchar(255),
    price           DOUBLE,
    connection_cost DOUBLE,
    group_id        int,
    PRIMARY KEY (id)
);

CREATE TABLE tariff
(
    id    int NOT NULL AUTO_INCREMENT,
    name  varchar(255),
    price DOUBLE,
    old   BOOL,
    PRIMARY KEY (id)
);

CREATE TABLE tariff_option
(
    tariff_id int,
    option_id int,
    PRIMARY KEY (tariff_id, option_id),
    FOREIGN KEY (tariff_id) REFERENCES eCare_db.tariff (id),
    FOREIGN KEY (option_id) REFERENCES eCare_db.options (id)
);
CREATE TABLE user
(
    id       int NOT NULL AUTO_INCREMENT,
    login    varchar(255),
    password VARCHAR(255),
    blocked  tinyint(1),
    PRIMARY KEY (id)
);
CREATE TABLE customer
(
    id               int NOT NULL AUTO_INCREMENT,
    user_id          int,
    name             varchar(255),
    surname          varchar(255),
    birth_date       DATE,
    passport_details varchar(255),
    address          varchar(255),
    email            varchar(255),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES eCare_db.user (id)
);
CREATE TABLE contract
(
    id               int NOT NULL AUTO_INCREMENT,
    number           int,
    tariff_id        int,
    customer_id      int,
    blocked_by_user  tinyint(1),
    blocked_by_admin tinyint(1),
    PRIMARY KEY (id),
    FOREIGN KEY (tariff_id) REFERENCES eCare_db.tariff (id),
    FOREIGN KEY (customer_id) REFERENCES eCare_db.customer (id)
);

CREATE TABLE user_role
(
    user_id int NOT NULL,
    roles   enum("ROLE_CUSTOMER","ROLE_ADMIN","ROLE_EMPLOYEE"),
    PRIMARY KEY (user_id, roles),
    FOREIGN KEY (user_id) REFERENCES eCare_db.user (id)
);

create table phonenumber_seq
(
    id  BIGINT AUTO_INCREMENT,
    nextPhonenumber bigint null,
        PRIMARY KEY(id)
);
ALTER TABLE phonenumber_seq AUTO_INCREMENT=9210010000;


create table required_options
(
    id              int NOT NULL AUTO_INCREMENT,
    name             varchar(255),
    group_id        int,
    PRIMARY KEY(id)
);


