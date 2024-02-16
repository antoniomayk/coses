CREATE TABLE tasks
(
    id           UUID           NOT NULL DEFAULT gen_random_uuid(),
    title        VARCHAR(255)   NOT NULL DEFAULT '',
    content      VARCHAR(65535) NOT NULL DEFAULT '',
    completed    BOOLEAN        NOT NULL DEFAULT FALSE,
    completed_at TIMESTAMP(6) WITH TIME ZONE,
    created_at   TIMESTAMP(6) WITH TIME ZONE,
    modified_at  TIMESTAMP(6) WITH TIME ZONE,
    PRIMARY KEY (id)
)
