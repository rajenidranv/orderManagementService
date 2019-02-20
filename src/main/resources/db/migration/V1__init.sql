CREATE TABLE customer (
  customer_id bigint(20) NOT NULL AUTO_INCREMENT,
  customer_name varchar(100) NOT NULL,
  contact_no bigint(20),
  address varchar(50),
  gender varchar(2),
  PRIMARY KEY (customer_id)
);


--insert into customer(customer_id,customer_name,contact_no,address,gender) values (1001,'Martin',89789898,'Englewood','M');