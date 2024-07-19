create table project.response
(
    id              int auto_increment comment 'response id PK'
        primary key,
    user_response   varchar(30)       not null,
    response_detail varchar(200)      null,
    response_file   varchar(300)      null,
    create_time     datetime          not null,
    update_time     datetime          not null,
    response_title  varchar(30)       null,
    schedule_date   datetime          null,
    request_id      int               null,
    response_state  tinyint default 0 null comment '0 pending, 1 accepted, 2 declined',
    cost            int               not null
);

