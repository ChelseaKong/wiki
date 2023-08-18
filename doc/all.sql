drop table if exists `test`;

create table `test` (
    `id` bigint not null comment 'id',
    `name` varchar(50) comment 'name',
    `password` varchar(36) comment 'password',
    primary key (`id`)
) engine = InnoDB default charset=utf8mb4 comment='test';

insert into `test` (id, name, password) values (01, 'test', 'password');