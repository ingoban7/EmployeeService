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