create table payment_type (
id bigint auto_increment,
name varchar(255),
constraint pk_payment_type primary key (id)
) engine = innodb character set = utf8 collate utf8_general_ci;

INSERT INTO payment_type (id, name) VALUES (1, "Készpénzes fizetés");
INSERT INTO payment_type (id, name) VALUES (2, "Online bankkártyás fizetés");

ALTER TABLE delivery
ADD payment_type_id BIGINT DEFAULT 1,
ADD constraint fk_payment_type foreign key (payment_type_id) REFERENCES payment_type(id);
