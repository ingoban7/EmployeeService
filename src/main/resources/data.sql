INSERT INTO Employee (first_name, last_name, short_id, email, phone, address) VALUES
  ('Tom', 'Alter', 'talter', 'abc@gmail.com','123456788','994 E South Union'),
  ('James', 'Gates', 'jgates', 'abc@microsoft.com','90123232','901 Crestwood Road');
create table Employee
(
	id int auto_increment,
	last_name varchar(250) not null,
	short_id varchar(50) null,
	email varchar(250) null,
	phone varchar(250) not null,
	address varchar(500) not null,
	first_name varchar(250) not null,
	constraint Employee_pk
		primary key (id)
);

