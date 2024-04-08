create table category (
id bigint auto_increment,
name varchar(255),
ordinal bigint,
constraint pk_category primary key (id)
) engine = innodb character set = utf8 collate utf8_general_ci;

INSERT INTO category(id, name, ordinal) VALUES
(1, 'Nincs kategória', 1),
(2, 'Társasjáték kártyák', 2),
(3, 'Partijátékok', 3),
(4, 'Kártyajáték kereskedelmi', 5),
(5, 'Oktató kártyajátékok', 4),
(6, 'Stratégiai kártyajátékok', 6);

create table products (
id bigint auto_increment,
code varchar(255) NOT NULL UNIQUE KEY,
address varchar(255) NOT NULL UNIQUE KEY,
name varchar(255) NOT NULL,
manufacture varchar(255) NOT NULL,
price bigint NOT NULL,
product_status varchar(50) NOT NULL,
category_id BIGINT DEFAULT 1,
constraint pk_products primary key (id),
constraint fk_category_products foreign key (category_id) REFERENCES category(id) on delete set null ON UPDATE CASCADE
)
engine = innodb character set = utf8 collate utf8_general_ci;

INSERT INTO products (code, name, address, manufacture, product_status, price, category_id) VALUES
('BG123', 'Settlers of Catan', 'settlers_of_catan', 'Catan Studio', 'ACTIVE', 15000, 2),
('PG456', 'Codenames', 'codenames', 'Czech Games Edition', 'ACTIVE', 6000, 3),
('TCG789', 'Magic: The Gathering Booster Box', 'magic_booster', 'Wizards of the Coast', 'ACTIVE', 36000, 5),
('ECG101', 'Math Flash Cards', 'math_flashcards', 'Educational Insights', 'ACTIVE', 5000, 4),
('SCG202', 'Dominion', 'dominion', 'Rio Grande Games', 'ACTIVE', 9000, 6),
('BG789', 'Ticket to Ride', 'ticket_to_ride', 'Days of Wonder', 'ACTIVE', 13500, 2),
('PG912', 'Cards Against Humanity', 'cards_against_humanity', 'Cards Against Humanity LLC', 'ACTIVE', 7500, 3),
('TCG345', 'Yu-Gi-Oh! Starter Deck', 'yugioh_starter_deck', 'Konami', 'ACTIVE', 4500, 5),
('ECG678', 'Spelling Bee Flash Cards', 'spelling_flashcards', 'Scholastic', 'ACTIVE', 3200, 4),
('SCG543', 'Race for the Galaxy', 'race_galaxy', 'Rio Grande Games', 'ACTIVE', 10500, 6),
('BG876', 'Pandemic', 'pandemic', 'Z-Man Games', 'ACTIVE', 12000, 2),
('PG234', 'Uno', 'uno', 'Mattel', 'ACTIVE', 5000, 3),
('TCG567', 'Pokémon TCG Booster Pack', 'pokemon_booster', 'The Pokémon Company', 'ACTIVE', 3000, 5),
('ECG890', 'Science Quiz Flash Cards', 'science_flashcards', 'Educational Insights', 'ACTIVE', 6000, 4),
('SCG678', '7 Wonders', 'seven_wonders', 'Repos Production', 'ACTIVE', 15000, 6),
('BG321', 'Risk', 'risk', 'Hasbro', 'ACTIVE', 10500, 2),
('PG654', 'Jenga', 'jenga', 'Hasbro', 'ACTIVE', 7500, 3),
('TCG987', 'Cardfight!! Vanguard Trial Deck', 'cardfight_vanguard', 'Bushiroad', 'ACTIVE', 9000, 5),
('ECG543', 'Geography Flash Cards', 'geography_flashcards', 'Carson-Dellosa', 'ACTIVE', 4000, 4),
('SCG210', 'Splendor', 'splendor', 'Space Cowboys', 'ACTIVE', 12000, 6),
('BG111', 'Risk: Legacy', 'risk_legacy', 'Hasbro', 'ACTIVE', 18000, 2),
('PG222', 'The Resistance', 'the_resistance', 'Indie Boards & Cards', 'ACTIVE', 9000, 3);