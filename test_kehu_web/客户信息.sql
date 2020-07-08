create table tb_customer(
cust_id number primary key,
cust_name varchar2(30),
phone varchar2(30),
mobile varchar2(30),
email varchar2(30),
fax varchar2(30),
address varchar2(50)
);
--序列与触发器
--创建用户表序列
create sequence seq_customer;

--创建用户表自动增长触发器
create trigger tri_customer_auto
before insert
on tb_customer
for each row
begin
    select seq_customer.nextval into :new.cust_id from dual;
end;
/
select * from tb_customer;

insert into tb_customer(cust_name,phone,mobile,email,fax,address)
values('易烊千玺','0731-908889','18474601322','123@qq.com','0731-908889','上海浦东');

insert into tb_customer(cust_name,phone,mobile,email,fax,address)
values('王昭君','0731-908889','18474601322','123@qq.com','0731-908889','上海浦东');

insert into tb_customer(cust_name,phone,mobile,email,fax,address)
values('小乔','0731-908889','18474601322','123@qq.com','0731-908889','上海浦东');

insert into tb_customer(cust_name,phone,mobile,email,fax,address)
values('李白','0731-908889','18474601322','123@qq.com','0731-908889','上海浦东');