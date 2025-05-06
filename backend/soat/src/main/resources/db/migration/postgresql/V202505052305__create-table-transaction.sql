-- create payment_method table
CREATE TYPE payment_method AS ENUM (
    'QRCODE'
);

-- create transaction_status table
CREATE TYPE transaction_status AS ENUM (
    'PENDING',
    'PAID',
    'FAILED'
);

-- create transaction table
CREATE TABLE transaction (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    method payment_method NOT NULL,
    order_id UUID NOT NULL REFERENCES "order"(id) ON DELETE CASCADE,
    qr_code TEXT NOT NULL,
    value NUMERIC(10, 2) NOT NULL CHECK (value > 0),
    status transaction_status NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    processed_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);
