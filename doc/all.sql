drop table if exists `test`;
drop table if exists `usertest`;

create table usertest (
    user_id				varchar(36)				not null,
    password			varchar(36)				not null,
    primary key(user_id)
) engine = InnoDB charset = utf8mb4;

create table `test` (
    `id` bigint not null comment 'id',
    `name` varchar(50) comment 'name',
    `password` varchar(36) comment 'password',
    primary key (`id`)
) engine = InnoDB default charset=utf8mb4 comment='test';