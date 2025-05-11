-- make user columns unique
ALTER TABLE customer ADD CONSTRAINT email UNIQUE (email);
ALTER TABLE customer ADD CONSTRAINT phone UNIQUE (phone);
ALTER TABLE customer ADD CONSTRAINT document_identifier UNIQUE (document_identifier);