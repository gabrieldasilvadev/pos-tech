-- increase currency precision
ALTER TABLE "order" ALTER COLUMN total_price TYPE NUMERIC(18, 4) USING total_price::NUMERIC(18, 4);
ALTER TABLE "order" ALTER COLUMN discount TYPE NUMERIC(18, 4) USING discount::NUMERIC(18, 4);
ALTER TABLE order_item ALTER COLUMN unit_price TYPE NUMERIC(18, 4) USING unit_price::NUMERIC(18, 4);
ALTER TABLE order_item ALTER COLUMN discount TYPE NUMERIC(18, 4) USING discount::NUMERIC(18, 4);
ALTER TABLE transaction ALTER COLUMN value TYPE NUMERIC(18, 4) USING value::NUMERIC(18, 4);
ALTER TABLE product ALTER COLUMN price TYPE NUMERIC(18, 4) USING price::NUMERIC(18, 4);