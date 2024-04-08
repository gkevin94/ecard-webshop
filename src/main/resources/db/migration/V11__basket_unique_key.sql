ALTER TABLE basket
ADD CONSTRAINT basket_user_product UNIQUE (user_id, product_id),
MODIFY pieces INT NOT NULL DEFAULT 1;