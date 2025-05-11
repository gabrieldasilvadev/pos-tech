-- rename order discount (clearer that it is not a percentage)
ALTER TABLE "order" RENAME COLUMN discount TO discount_amount;
ALTER TABLE order_item RENAME COLUMN discount TO discount_amount;