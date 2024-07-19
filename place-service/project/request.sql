create table project.request
(
    id             int auto_increment comment 'request id PK'
        primary key,
    user_request   varchar(12)                  not null comment 'username in user',
    request_type   tinyint unsigned default '0' null,
    request_title  varchar(30)                  not null,
    request_detail varchar(200)                 null,
    request_file   varchar(300)                 null,
    max_cost       mediumint unsigned           null,
    end_time       datetime                     null,
    create_time    datetime                     not null,
    update_time    datetime                     not null,
    request_state  tinyint unsigned default '0' null,
    location       varchar(6)                   not null
);

