CREATE TABLE tasks
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(128),
    payload     VARCHAR,
    is_done     BOOLEAN                  DEFAULT FALSE,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT now(),
    finished_at TIMESTAMP WITH TIME ZONE,
    user_id     INT REFERENCES users (id)
);