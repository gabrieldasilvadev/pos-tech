-- leave all foreign key without explicit references (i.e. without "REFERENCES")
ALTER TABLE orders DROP CONSTRAINT order_customer_id_fkey;
ALTER TABLE order_items DROP CONSTRAINT order_item_order_id_fkey;
ALTER TABLE order_items DROP CONSTRAINT order_item_product_id_fkey;
ALTER TABLE transactions DROP CONSTRAINT transaction_order_id_fkey;