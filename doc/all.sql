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

insert into `ebook` (id, name, category1_id, category2_id, description, cover, doc_count, view_count, vote_count) values
                    (1, 'Spring Boot 入门教程', 1, 2, '零基础入门 Java 开发，企业级应用开发最佳首选框架', '/image/cover1.png', 5, 6, 7);
insert into `ebook` (id, name, description, cover) values (2, 'Vue 入门教程', '零基础入门 Vue 开发，企业级应用开发最佳首选框架', '/image/cover2.png');
insert into `ebook` (id, name, description) values (3, 'Python 入门教程', '零基础入门 Python 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (4, 'Mysql 入门教程', '零基础入门 Mysql 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (5, 'Oracle 入门教程', '零基础入门 Oracle 开发，企业级应用开发最佳首选框架');

### add table category
drop table if exists `category`;
create table `category` (
    `id` bigint not null comment 'id',
    `parent` bigint not null default 0 comment 'father id',
    `name` varchar(50) not null comment 'name',
    `sort` int comment 'sort',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='category';

insert into `category` (id, parent, name, sort) values (100, 000, '前端开发', 100);
insert into `category` (id, parent, name, sort) values (101, 100, 'Vue', 101);
insert into `category` (id, parent, name, sort) values (102, 100, 'HTML & CSS', 102);
insert into `category` (id, parent, name, sort) values (200, 000, 'Java', 200);
insert into `category` (id, parent, name, sort) values (201, 200, '基础应用', 201);
insert into `category` (id, parent, name, sort) values (202, 200, '框架应用', 202);
insert into `category` (id, parent, name, sort) values (300, 000, 'Python', 300);
insert into `category` (id, parent, name, sort) values (301, 300, '基础应用', 301);
insert into `category` (id, parent, name, sort) values (302, 300, '进阶方向应用', 302);
insert into `category` (id, parent, name, sort) values (400, 000, '数据库', 400);
insert into `category` (id, parent, name, sort) values (401, 400, 'MySQL', 401);
insert into `category` (id, parent, name, sort) values (500, 000, '其它', 500);
insert into `category` (id, parent, name, sort) values (501, 500, '服务器', 501);
insert into `category` (id, parent, name, sort) values (502, 500, '开发工具', 502);
insert into `category` (id, parent, name, sort) values (503, 500, '热门服务端语言', 503);