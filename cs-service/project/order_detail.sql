create table project.order_detail
(
    id            int auto_increment comment 'order id PK'
        primary key,
    request_id    int                           not null,
    user_request  varchar(30)                   not null comment 'PK in user',
    user_response varchar(30)                   not null comment 'PK in user',
    order_time    datetime                      not null,
    request_fee   smallint unsigned default '2' null,
    response_fee  smallint unsigned default '2' null,
    response_id   int                           not null,
    location      varchar(6)                    not null,
    type          tinyint                       not null
);

