CREATE
EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE event_store
(
    id           UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    aggregate_id UUID         NOT NULL,
    event_type   VARCHAR(255) NOT NULL,
    payload      TEXT         NOT NULL,
    occurred_on  TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    version      INTEGER      NOT NULL,
    metadata     JSONB
);

CREATE INDEX idx_event_store_aggregate ON event_store (aggregate_id);
CREATE INDEX idx_event_store_type ON event_store (event_type);
-- CREATE INDEX idx_event_store_metadata ON event_store USING GIN (metadata);
