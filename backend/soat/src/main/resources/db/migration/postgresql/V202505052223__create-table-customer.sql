-- enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- create customer table
CREATE TABLE customer (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    document_identifier VARCHAR(255) NOT NULL
);
