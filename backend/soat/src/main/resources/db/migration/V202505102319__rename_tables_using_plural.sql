-- use plurals in table names
ALTER TABLE customer RENAME TO customers;
ALTER TABLE product RENAME TO products;
ALTER TABLE "order" RENAME TO orders;
ALTER TABLE order_item RENAME TO order_items;
ALTER TABLE transaction RENAME TO transactions;