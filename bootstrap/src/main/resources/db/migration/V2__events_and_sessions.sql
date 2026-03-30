CREATE TABLE category
(
    id         VARCHAR(255) NOT NULL,
    type       VARCHAR(32)  NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL,
    updated_at TIMESTAMPTZ  NOT NULL,
    CONSTRAINT category_pkey PRIMARY KEY (id),
    CONSTRAINT category_type_key UNIQUE (type)
);

CREATE TABLE events
(
    id                VARCHAR(255) NOT NULL,
    category_id       VARCHAR(255) NOT NULL,
    title             VARCHAR(200) NOT NULL,
    normalized_title  VARCHAR(200) NOT NULL,
    description       TEXT,
    cover_image_ref   VARCHAR(2048),
    slug              VARCHAR(120),
    status            VARCHAR(32)  NOT NULL,
    created_at        TIMESTAMPTZ  NOT NULL,
    updated_at        TIMESTAMPTZ  NOT NULL,
    CONSTRAINT events_pkey PRIMARY KEY (id),
    CONSTRAINT events_category_fkey FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE sessions
(
    id          VARCHAR(255) NOT NULL,
    event_id    VARCHAR(255) NOT NULL,
    capacity    INTEGER      NOT NULL,
    status      VARCHAR(32)  NOT NULL,
    started_at  TIMESTAMPTZ  NOT NULL,
    ends_at     TIMESTAMPTZ  NOT NULL,
    created_at  TIMESTAMPTZ  NOT NULL,
    updated_at  TIMESTAMPTZ  NOT NULL,
    CONSTRAINT sessions_pkey PRIMARY KEY (id),
    CONSTRAINT sessions_event_fkey FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);

CREATE INDEX idx_events_category ON events (category_id);
CREATE INDEX idx_events_category_norm_title ON events (category_id, normalized_title);
CREATE INDEX idx_sessions_event ON sessions (event_id);

INSERT INTO category (id, type, created_at, updated_at)
VALUES ('b0000001-0000-4000-8000-000000000001', 'MOVIE', NOW(), NOW()),
       ('b0000001-0000-4000-8000-000000000002', 'TRIP', NOW(), NOW()),
       ('b0000001-0000-4000-8000-000000000003', 'THEATRE', NOW(), NOW());
