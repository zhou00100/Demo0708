create table users(
userid number primary key,
username varchar2(20),
userpassword varchar2(20)
);
--序列与触发器
--创建用户表序列
create sequence seq_users;

--创建用户表自动增长触发器
create trigger tri_users_auto
before insert
on users
for each row
begin
    select seq_users.nextval into :new.userid from dual;
end;
/
select * from users;

create table classinfo(
name varchar2(10) primary key,
age number,
sex varchar2(2),
class varchar(10),
address varchar(20)
);

  CREATE TABLE TBL_STUINFO(
  STU_ID NUMBER(5,0) primary key, --学生编号
  STU_NAME VARCHAR2(20) NOT NULL,  --学生姓名
  STU_SEX CHAR(3),  --学生性别
  STU_BIRTHDAY DATE DEFAULT sysdate, --学生生日
  STU_AGE NUMBER(3,0) --学生年龄
   ) ;
   --创建学生表自动增长触发器
create trigger tri_student_auto
before insert
on TBL_STUINFO
for each row
begin
    select seq_student.nextval into :new.STU_ID from dual;
end;
/
select * from TBL_STUINFO;

--创建账目数据库
create table tally(
       tallyid number not null primary key,
       type varchar2(20) not null,
       price number(10,2) not null,
       paydate date default sysdate not null
);

create sequence tally_seq
start with 1
increment by 1;

insert into tally values(tally_seq.nextval,'上衣',300,to_date('2014-08-12 09:23:12','yyyy-MM-dd HH24:MI:SS'));
insert into tally values(tally_seq.nextval,'西装',1000,to_date('2014-09-12 19:20:12','yyyy-MM-dd HH24:MI:SS'));
insert into tally values(tally_seq.nextval,'可乐',2.5,to_date('2014-08-22 09:23:12','yyyy-MM-dd HH24:MI:SS'));
insert into tally values(tally_seq.nextval,'雪碧',3,to_date('2015-07-12 12:23:12','yyyy-MM-dd HH24:MI:SS'));

select * from tally;

select * from
(select row_number() over(order by stu_id) no, s.* from tbl_stuinfo s) temp 
where temp.no between 1 and 2;

select * from tbl_stuinfo;