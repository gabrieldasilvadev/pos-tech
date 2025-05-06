-- create order table
CREATE TABLE "order" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL REFERENCES customer(id),
    total_price NUMERIC(10, 2) NOT NULL CHECK (total_price > 0),
    discount NUMERIC(10, 2) DEFAULT 0 CHECK (discount >= 0),
    observation VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ
) PARTITION BY RANGE (created_at);

-- create order_item table
CREATE TABLE order_item (
    id UUID PRIMARY KEY DEFAULT en_random_uuid(),
    order_id UUID NOT NULL REFERENCES "order"(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES product(id),
    product_name VARCHAR(255) NOT NULL,  -- Denormalized for historical accuracy
    product_quantity INTEGER NOT NULL CHECK (product_quantity > 0),
    unit_price NUMERIC(10, 2) NOT NULL CHECK (unit_price > 0),
    discount NUMERIC(10, 2) DEFAULT 0 CHECK (discount >= 0),
    observation VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ
);
