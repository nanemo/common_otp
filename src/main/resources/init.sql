create database otp_db;

create table common_otp
(
    otp_id        bigint primary key,
    module        varchar(100),
    phone_number  varchar(13),
    sms_text      text,
    password      varchar(64),
    instance_date timestamp,
    retry_count   integer,
    status        integer
)