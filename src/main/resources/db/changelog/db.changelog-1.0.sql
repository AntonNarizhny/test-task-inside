--liquibase formatted sql

--changeset anton_narizhny:1
SET search_path = "public";

--changeset anton_narizhny:2
CREATE TABLE IF NOT EXISTS client
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE ,
    password VARCHAR(128) NOT NULL ,
    token TEXT
);

--changeset anton_narizhny:3
CREATE TABLE IF NOT EXISTS message
(
    id BIGSERIAL PRIMARY KEY ,
    message TEXT,
    client_id INT REFERENCES client (id)
);