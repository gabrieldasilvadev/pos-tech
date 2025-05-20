ALTER TABLE orders
    ADD COLUMN status VARCHAR NOT NULL;

ALTER TABLE orders
    ADD CONSTRAINT chk_status_valid CHECK (
        status IN (
                   'RECEIVED',
                   'AWAITING_PAYMENT',
                   'PAID',
                   'IN_PREPARATION',
                   'DONE',
                   'DELIVERED'
            )
        );
