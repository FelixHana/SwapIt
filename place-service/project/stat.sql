create table project.stat
(
    stat_date     date                     not null,
    stat_location int                      not null comment 'PK in sys_position',
    request_type  tinyint unsigned         not null,
    order_count   int unsigned default '0' null,
    stat_fee      int unsigned default '0' null
);

