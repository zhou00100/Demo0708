create table tb_customer(
cust_id number primary key,
cust_name varchar2(30),
phone varchar2(30),
mobile varchar2(30),
email varchar2(30),
fax varchar2(30),
address varchar2(50)
);
--�����봥����
--�����û�������
create sequence seq_customer;

--�����û����Զ�����������
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
values('����ǧ��','0731-908889','18474601322','123@qq.com','0731-908889','�Ϻ��ֶ�');

insert into tb_customer(cust_name,phone,mobile,email,fax,address)
values('���Ѿ�','0731-908889','18474601322','123@qq.com','0731-908889','�Ϻ��ֶ�');

insert into tb_customer(cust_name,phone,mobile,email,fax,address)
values('С��','0731-908889','18474601322','123@qq.com','0731-908889','�Ϻ��ֶ�');

insert into tb_customer(cust_name,phone,mobile,email,fax,address)
values('���','0731-908889','18474601322','123@qq.com','0731-908889','�Ϻ��ֶ�');