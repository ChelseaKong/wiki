drop table if exists `test`;
create table `test` (
    `id` bigint not null comment 'id',
    `name` varchar(50) comment 'name',
    `password` varchar(36) comment 'password',
    primary key (`id`)
) engine = InnoDB default charset=utf8mb4 comment='test';

insert into `test` (id, name, password) values (01, 'test', 'password');

# add table demo
drop table if exists `demo`;
create table `demo` (
     `id` bigint not null comment 'id',
     `name` varchar(50) comment 'name',
     primary key (`id`)
) engine = InnoDB default charset=utf8mb4 comment='test';

insert into `demo` (id, name) values (01, 'test');

### add table ebook
drop table if exists `ebook`;
create table `ebook` (
    `id` bigint not null comment 'id',
    `name` varchar(50) comment 'Title',
    `category1_id` bigint comment 'Category1',
    `category2_id` bigint comment 'Category2',
    `description` varchar(200) comment 'Description',
    `cover` varchar(200) comment 'Cover',
    `doc_count` int not null default 0 comment 'Documents',
    `view_count` int not null default 0 comment 'ViewCount',
    `vote_count` int not null default 0 comment 'LikeCount',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='eBook';

insert into `ebook` (id, name, description, cover) values (1, 'Spring Boot 入门教程', '零基础入门 Java 开发，企业级应用开发最佳首选框架', '/image/cover1.png');
insert into `ebook` (id, name, description, cover) values (2, 'Vue 入门教程', '零基础入门 Vue 开发，企业级应用开发最佳首选框架', '/image/cover2.png');
insert into `ebook` (id, name, description) values (3, 'Python 入门教程', '零基础入门 Python 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (4, 'Mysql 入门教程', '零基础入门 Mysql 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (5, 'Oracle 入门教程', '零基础入门 Oracle 开发，企业级应用开发最佳首选框架');

