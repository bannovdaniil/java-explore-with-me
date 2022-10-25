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
        CONSTRAINT annotation_length CHECK (char_length(annotation) >= 20 AND char_length(annotation) <= 2000),
    category_id        BIGINT NOT NULL
        CONSTRAINT events_categories_fkey REFERENCES categories,
    confirmed_requests INTEGER,
    created_on         TIMESTAMP,
    description        TEXT,
    CONSTRAINT description_length CHECK (char_length(description) >= 20 AND char_length(description) <= 7000),
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
        CONSTRAINT title_length CHECK (char_length(title) >= 3),
    views              BIGINT  DEFAULT 0
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title  VARCHAR(120),
    pinned BOOLEAN default false
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_id       BIGINT REFERENCES events (id),
    compilation_id BIGINT REFERENCES compilations (id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    requester_id BIGINT REFERENCES users (id),
    created      TIMESTAMP,
    status       VARCHAR(15),
    event_id     BIGINT REFERENCES events (id),
    UNIQUE (event_id, requester_id)
);
