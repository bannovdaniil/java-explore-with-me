DROP TABLE IF EXISTS compilations cascade;
DROP TABLE IF EXISTS requests cascade;
DROP TABLE IF EXISTS events cascade;
DROP TABLE IF EXISTS users cascade;
DROP TABLE IF EXISTS categories cascade;
DROP TABLE IF EXISTS locations cascade;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name  VARCHAR(250) NOT NULL,
    email VARCHAR(100) UNIQUE,
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200),
    CONSTRAINT UQ_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS locations
(
    id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lat FLOAT,
    lon FLOAT
);


CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation         TEXT
        CONSTRAINT anotationlng CHECK (char_length(annotation) >= 20 AND char_length(annotation) <= 2000),
    category_id        BIGINT NOT NULL
        CONSTRAINT events_categories_fkey REFERENCES categories,
    confirmed_requests INTEGER,
    created_on         TIMESTAMP,
    description        TEXT,
    CONSTRAINT descriptionlng CHECK (char_length(description) >= 20 AND char_length(description) <= 7000),
    event_date         TIMESTAMP,
    initiator_id       BIGINT NOT NULL
        CONSTRAINT events_users_fkey REFERENCES users,
    location_id        BIGINT NOT NULL
        CONSTRAINT events_locations_fkey REFERENCES locations,
    paid               BOOLEAN default false,
    participant_limit  INTEGER DEFAULT 0,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN DEFAULT true,
    state              VARCHAR(15),
    title              VARCHAR(120)
        CONSTRAINT titlelng CHECK (char_length(title) >= 3),
    views              BIGINT  DEFAULT 0
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
