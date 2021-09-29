INSERT INTO Employee (first_name, last_name, short_id, email, phone, address, role) VALUES
  ('Tom', 'Alter', 'talter', 'abc@gmail.com','123456788','994 E South Union', 'HR'),
  ('James', 'Gates', 'jgates', 'abc@microsoft.com','90123232','901 Crestwood Road','Software Engineer');
DROP TABLE IF EXISTS Employee;
create table Employee
(
	id int auto_increment,
	last_name varchar(250) not null,
	short_id varchar(50) not null,
	email varchar(250) not null,
	phone varchar(250) null,
	address varchar(500) null,
	first_name varchar(250) not null,
	role varchar(250) not null,
	constraint Employee_pk
		primary key (id)
);

