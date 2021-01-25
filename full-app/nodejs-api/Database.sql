FLUSH PRIVILEGES;
ALTER USER "root"@"localhost" IDENTIFIED BY "password";
Create database sms;
Use sms;

Create table smsQ(
    id int AUTO_INCREMENT,
    phone varchar(10) not null,
    body varchar(140) not null,
    sentflag tinyint(1) default 0,
    ts TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    Primary Key(id)
);


insert into smsQ (phone, body) values ("1095285510", "test my phone number");
insert into smsQ (phone, body) values ("1153896193", "test my other phone number");
insert into smsQ (phone, body,sentflag) values ("1095285510", "sent flag truee",1);
insert into smsQ (phone, body,sentflag) values ("1153896193", "sent flag false",0);