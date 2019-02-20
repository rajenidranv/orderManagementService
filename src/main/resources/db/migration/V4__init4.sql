CREATE TABLE orders (
  order_id bigint(20) NOT NULL ,
  customer_id bigint(20) NOT NULL,
  payment_channel varchar(5) NOT NULL,
  cod boolean,
  order_status varchar(25) NOT NULL,
  order_created_on DATE,
  total_amount double(50),
  shipping_address  varchar(50),
  PRIMARY KEY (order_id)
);


CREATE TABLE order_line_item (
  order_line_item_id bigint(20) NOT NULL ,
  order_id bigint(20),
  sku_id bigint(20) NOT NULL,
  item_qty int,
  PRIMARY KEY (order_line_item_id),
  FOREIGN KEY (order_id) REFERENCES orders(order_id)
);


insert into orders(order_id,customer_id,payment_channel,cod,order_status,order_created_on,total_amount,shipping_address) values (2001,1001,'CC',true,'Finished',null,100.05,'Englewood');

insert into order_line_item(order_line_item_id,order_id,sku_id,item_qty) values (3001,2001,4001,5);

insert into order_line_item(order_line_item_id,order_id,sku_id,item_qty) values (3002,2001,4002,1);