create table project.summary_table
(
    month_year  varchar(7)  not null,
    region      varchar(50) not null,
    order_type  tinyint     not null,
    order_count int         null,
    primary key (month_year, region, order_type)
);

