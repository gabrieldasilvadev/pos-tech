-- decrease customer_document_type length
ALTER TABLE customer ALTER COLUMN document_identifier TYPE VARCHAR(11) USING document_identifier::VARCHAR(11);