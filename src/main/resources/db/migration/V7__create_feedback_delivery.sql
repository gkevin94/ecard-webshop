CREATE TABLE feedback (
id bigint auto_increment,
feedback_date datetime,
feedback varchar(255),
rating tinyint,
user_id bigint,
product_id bigint,
constraint pk_feedback primary key (id)
) engine = innodb character set = utf8 collate utf8_general_ci;

CREATE TABLE delivery (
id bigint auto_increment,
address varchar(255),
user_id bigint,
constraint pk_delivery primary key (id),
constraint fk_delivery_users foreign key (user_id) REFERENCES users(id)
) engine = innodb character set = utf8 collate utf8_general_ci;