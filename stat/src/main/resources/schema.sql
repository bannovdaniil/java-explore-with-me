DROP TABLE IF EXISTS stats cascade;

CREATE TABLE IF NOT EXISTS stats
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    app       VARCHAR(30)  NOT NULL,
    uri       VARCHAR(250) NOT NULL,
    ip        VARCHAR(50)  NOT NULL,
    timestamp TIMESTAMP
);