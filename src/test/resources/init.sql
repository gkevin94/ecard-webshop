delete from feedback;
delete from ordered_products;
delete from orders;
delete from delivery;
delete from basket;
delete from users;
delete from images;
delete from products;
delete from category;

INSERT INTO users(id, name, user_name, password, enabled, role, user_status) VALUES
(1, 'admin', 'admin', 'admin', 1, 'ROLE_ADMIN', 'ACTIVE'),
(2, 'user', 'user', 'user', 1, 'ROLE_USER', 'ACTIVE'),
(3, 'admin2', 'admin2', '$2a$04$LjyBhChXHNOl.Q/N6QQijeDWaf5Qp8S7w5f3mlxYRwWX/gQtvNAs.', 1, 'ROLE_ADMIN', 'ACTIVE');

INSERT INTO orders(id, purchase_date, user_id, order_status) VALUES
(1, '2023-11-20 20:20:20', 2, 'ACTIVE'),
(2, '2023-09-20 20:20:20', 2, 'SHIPPED'),
(3, '2023-12-20 20:20:20', 2, 'DELETED'),
(4, '2023-12-22 20:20:20', 1, 'SHIPPED'),
(5, '2023-12-11 20:20:20', 2, 'SHIPPED');

INSERT INTO category(id, name, ordinal) values
(1, 'Nincs kategória', 1),
(2, 'Társasjáték kártyák', 2),
(3, 'Partijátékok', 3),
(4, 'Kártyajáték kereskedelmi', 5),
(5, 'Oktató kártyajátékok', 4),
(6, 'Stratégiai kártyajátékok', 6);

INSERT INTO products (id, code, name, address, manufacture, product_status, price, category_id) VALUES
(1, 'BG123', 'Settlers of Catan', 'settlers_of_catan', 'Catan Studio', 'ACTIVE', 15000, 2),
(2, 'PG456', 'Codenames', 'codenames', 'Czech Games Edition', 'ACTIVE', 6000, 3),
(3, 'TCG789', 'Magic: The Gathering Booster Box', 'magic_booster', 'Wizards of the Coast', 'ACTIVE', 36000, 5),
(4, 'ECG101', 'Math Flash Cards', 'math_flashcards', 'Educational Insights', 'ACTIVE', 5000, 4),
(5, 'SCG202', 'Dominion', 'dominion', 'Rio Grande Games', 'ACTIVE', 9000, 6),
(6, 'BG789', 'Ticket to Ride', 'ticket_to_ride', 'Days of Wonder', 'ACTIVE', 13500, 2),
(7, 'PG912', 'Cards Against Humanity', 'cards_against_humanity', 'Cards Against Humanity LLC', 'ACTIVE', 7500, 3),
(8, 'TCG345', 'Yu-Gi-Oh! Starter Deck', 'yugioh_starter_deck', 'Konami', 'ACTIVE', 4500, 5),
(9, 'ECG678', 'Spelling Bee Flash Cards', 'spelling_flashcards', 'Scholastic', 'ACTIVE', 3200, 4),
(10, 'SCG543', 'Race for the Galaxy', 'race_galaxy', 'Rio Grande Games', 'ACTIVE', 10500, 6),
(11, 'BG876', 'Pandemic', 'pandemic', 'Z-Man Games', 'ACTIVE', 12000, 2),
(12, 'PG234', 'Uno', 'uno', 'Mattel', 'ACTIVE', 5000, 3),
(13, 'TCG567', 'Pokémon TCG Booster Pack', 'pokemon_booster', 'The Pokémon Company', 'ACTIVE', 3000, 5),
(14, 'ECG890', 'Science Quiz Flash Cards', 'science_flashcards', 'Educational Insights', 'ACTIVE', 6000, 4),
(15, 'SCG678', '7 Wonders', 'seven_wonders', 'Repos Production', 'ACTIVE', 15000, 6),
(16, 'BG321', 'Risk', 'risk', 'Hasbro', 'ACTIVE', 10500, 2),
(17, 'PG654', 'Jenga', 'jenga', 'Hasbro', 'ACTIVE', 7500, 3),
(18, 'TCG987', 'Cardfight!! Vanguard Trial Deck', 'cardfight_vanguard', 'Bushiroad', 'ACTIVE', 9000, 5),
(19, 'ECG543', 'Geography Flash Cards', 'geography_flashcards', 'Carson-Dellosa', 'ACTIVE', 4000, 4),
(20, 'SCG210', 'Splendor', 'splendor', 'Space Cowboys', 'ACTIVE', 12000, 6),
(21, 'BG111', 'Risk: Legacy', 'risk_legacy', 'Hasbro', 'ACTIVE', 18000, 2),
(22, 'PG222', 'The Resistance', 'the_resistance', 'Indie Boards & Cards', 'ACTIVE', 9000, 3);

INSERT INTO ordered_products(product_id, order_id, ordering_price, ordering_name, pieces) VALUES
(1,1,15000, 'Settlers of Catan', 1),
(2,1,6000, 'Codenames', 2),
(6,2,13500, 'Ticket to Ride', 1),
(17,3,7500, 'Jenga', 2),
(16,4,10500, 'Risk', 1);

INSERT INTO basket(id, user_id, product_id) VALUES
(1, 2, 5),
(2, 2, 3),
(3, 1, 2),
(4, 3, 5);

INSERT INTO feedback (id, feedback_date, feedback, rating, user_id, product_id)
VALUES (1, '2023-12-03 10:10:10', 'Never a better shop!', 5,3,1);


INSERT into delivery (address, user_id) values ('Kiszállítás az üzeltbe', null);