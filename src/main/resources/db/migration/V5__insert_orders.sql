INSERT INTO orders(id, purchase_date, user_id,`total`, sum_quantity, order_status) VALUES
(1, '2023-10-20 20:20:20', 4, 21000, 2, 'ACTIVE'),
(2, '2023-12-21 20:20:20', 4, 13500, 1, 'SHIPPED'),
(3, '2023-11-20 20:20:20', 4, 7500, 1, 'DELETED'),
(4, '2023-11-11 20:20:20', 4, 10500, 1, 'SHIPPED');



INSERT INTO ordered_products(product_id, order_id, ordering_price, ordering_name) VALUES
(1,1,15000, 'Settlers of Catan'),
(2,1,6000, 'Codenames'),
(6,2,13500, 'Ticket to Ride'),
(17,3,7500, 'Jenga'),
(16,4,10500, 'Risk');