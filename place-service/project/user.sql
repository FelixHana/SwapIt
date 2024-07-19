create table project.user
(
    id            int auto_increment comment 'user id PK'
        primary key,
    user_name     varchar(12)                  not null,
    user_password varchar(128)                 not null,
    user_role     tinyint unsigned default '0' null,
    name          varchar(12)                  not null,
    id_type       tinyint unsigned default '0' null,
    id_number     varchar(20)                  null,
    phone         varchar(11)                  null,
    user_level    tinyint unsigned default '0' null,
    user_profile  varchar(100)                 null,
    location      int                          null comment 'PK in sys_position',
    create_time   datetime                     not null,
    update_time   datetime                     not null,
    user_salt     varchar(128)                 not null comment '用户盐值',
    constraint user_name
        unique (user_name)
);

