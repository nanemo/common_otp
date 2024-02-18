create database otp_db;

drop table common_otp;

create table common_otp
(
    id           bigint primary key,
    module       varchar(100),
    phone_number varchar(13),
    email        varchar(64),
    text         varchar(250),
    otp          varchar(9) not null,
    retry_count  integer,
    status       int,
    created      timestamp not null
);