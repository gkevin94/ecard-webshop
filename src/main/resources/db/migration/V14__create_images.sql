create table images (
    id bigint auto_increment,
    image_file mediumblob not null,
    file_type varchar(20) not null,
    file_name varchar(50) not null,
    product_id bigint,
    constraint pk_images primary key (id),
    constraint fk_product_images foreign key (product_id) REFERENCES products(id)
)
engine = innodb character set = utf8 collate utf8_general_ci;


