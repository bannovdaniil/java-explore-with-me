DROP TABLE IF EXISTS compilations cascade;
DROP TABLE IF EXISTS requests cascade;
DROP TABLE IF EXISTS events cascade;
DROP TABLE IF EXISTS users cascade;
DROP TABLE IF EXISTS category cascade;
DROP TABLE IF EXISTS locations cascade;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name  VARCHAR(250) NOT NULL,
    email VARCHAR(100) UNIQUE,
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS category
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS locations
(
    id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lat FLOAT,
    lon FLOAT
);


CREATE TABLE IF NOT EXISTS events
(
    id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation        VARCHAR(2000)
        CONSTRAINT anotationlng CHECK (char_length(annotation) >= 20),
    category          BIGINT NOT NULL
        CONSTRAINT events_category_fkey REFERENCES category,
    confirmedRequests BIGINT,
    createdOn         TIMESTAMP,
    description       VARCHAR(7000)
        CONSTRAINT descriptionlng CHECK (char_length(annotation) >= 20),
    eventDate         TIMESTAMP,
    initiator         BIGINT NOT NULL
        CONSTRAINT events_users_fkey REFERENCES users,
    location          BIGINT NOT NULL
        CONSTRAINT events_locations_fkey REFERENCES locations,
    paid              BOOLEAN default false,
    participantLimit  INT DEFAULT 0,
    publishedOn       TIMESTAMP,
    requestModeration BOOLEAN DEFAULT true,
    state             VARCHAR(15),
    title             VARCHAR(120)
        CONSTRAINT titlelng CHECK (char_length(title) >= 3),
    views             BIGINT
);

CREATE TABLE IF NOT EXISTS compilations
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pinned   BOOLEAN default false,
    title    VARCHAR(120),
    event_id BIGINT NOT NULL
        CONSTRAINT compilations_events_fkey REFERENCES events
);


CREATE TABLE IF NOT EXISTS requests
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    requester BIGINT NOT NULL
        CONSTRAINT requests_users_fkey REFERENCES users,
    title     VARCHAR(120),
    created   TIMESTAMP,
    status    VARCHAR(15),
    event_id  BIGINT NOT NULL
        CONSTRAINT requests_events_fkey REFERENCES events
);
