-- create products category table
CREATE TYPE product_category AS ENUM ('SNACK', 'DRINK', 'DESSERT', 'SIDE_DISH');

-- create product table
CREATE TABLE product (
    id UUID PRIMARY KEY gen_random_uuid(),
    sku VARCHAR(16),
    name VARCHAR(255) NOT NULL,
    category product_category NOT NULL,
    description VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    image VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
