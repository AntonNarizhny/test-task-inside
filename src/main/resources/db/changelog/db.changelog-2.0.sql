--liquibase formatted sql

--changeset anton_narizhny:1
INSERT INTO client(name, password)
VALUES ('user', '12345'),
       ('anton', '07111998'),
       ('test', '55555');