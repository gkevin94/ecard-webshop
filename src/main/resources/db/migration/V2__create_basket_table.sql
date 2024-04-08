create table users (
id bigint auto_increment,
name varchar(255),
email varchar(255) NULL,
user_name varchar(255) NOT NULL,
password varchar(255),
enabled int,
role ENUM('ROLE_USER','ROLE_ADMIN') DEFAULT 'ROLE_USER',
user_status ENUM('ACTIVE','DELETED') DEFAULT 'ACTIVE',
constraint pk_users primary key (id)
) engine = innodb character set = utf8 collate utf8_general_ci;

INSERT INTO users(name, email, user_name, password, enabled, role) VALUES
('John Doe', 'john.doe@gmail.com', 'johndoe', '$2y$12$heZ5nBAqUAvNALw5S0i17eyvbPEMJVErjd0ksuCX3neDvBbnvUj.G', 1, 'ROLE_USER'),
('admin', 'admin@cardshop.com', 'admin', '$2y$12$.kwH5V5PC1OzOEeI4AFj6.7.LJ9.w33z2L7HVKcxTe7KIgbU4GiKi', 1, 'ROLE_ADMIN');

create table basket (
id bigint auto_increment,
user_id bigint,
product_id bigint,
constraint pk_basket primary key (id),
constraint fk_user_basket foreign key (user_id) REFERENCES users(id),
constraint fk_product_basket foreign key (product_id) REFERENCES products(id)
) engine = innodb character set = utf8 collate utf8_general_ci;