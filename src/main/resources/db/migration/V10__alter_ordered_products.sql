UPDATE ordered_products set pieces = 1;

ALTER TABLE orders
DROP COLUMN sum_quantity,
DROP COLUMN total;

ALTER TABLE category
MODIFY ordinal BIGINT NOT NULL,
MODIFY name varchar(255) NOT NULL;