ALTER TABLE delivery ADD CONSTRAINT uc_delivery UNIQUE (user_id,address);
ALTER TABLE delivery MODIFY address VARCHAR(255) NOT NULL;