drop table if exists `test`;

create table `test`(
    `id`   bigint not null comment 'id',
    `name` varchar(50) comment '名称',
    `password` varchar(50) comment '密码',
    primary key (`id`)
)engine=innodb default charset =utf8mb4 comment ='测试';

select * from test;


insert into `test` (id, name, password) VALUES (1,'noah',password('password'));


create table `demo`(
                       `id`   bigint not null comment 'id',
                       `name` varchar(50) comment '名称',
                       primary key (`id`)
)engine=innodb default charset =utf8mb4 comment ='测试';

insert into `demo` (id, name) VALUES (1,'starry');



drop table if exists `ebook`;

create table `ebook`
(
    `id`   bigint not null comment 'id',
    `name` varchar(50) comment '名称',
    `category1_id` bigint comment '分类1',
    `category2_id` bigint comment '分类2',
    `description` varchar(200) comment '描述',
    `cover` varchar(200) comment '封面',
    `doc_count` int comment '文档数',
    `view_count` int comment '阅读数',
    `vote_count` int comment '点赞数',
    primary key (`id`)
)engine = innodb default charset = utf8mb4 comment ='电子书';

insert into `ebook`(id,name,description) values (1,'信息论','香农不出谁与争锋');
insert into `ebook`(id,name,description) values (2,'信号与系统','奥本海默大巧不工');
insert into `ebook`(id,name,description) values (3,'哥德堡变奏曲','巴赫统领江湖');
insert into `ebook`(id,name,description) values (4,'巴仁博伊姆','贝多芬当代诠释者');


drop table if exists `music`;

create table `music`
(
    `id`   bigint not null comment 'id',
    `name` varchar(50) comment '名称',
    `author` varchar(100) comment '作者',
    `category1_id` bigint comment '分类1',
    `category2_id` bigint comment '分类2',
    `description` varchar(200) comment '描述',
    `cover` varchar(200) comment '封面',
    `doc_count` int comment '文档数',
    `listen_count` int comment '听歌数',
    `vote_count` int comment '点赞数',
    primary key (`id`)
)engine = innodb default charset = utf8mb4 comment ='音乐';

insert into `music`(id,name,author) values (1,'哥德堡变奏曲','巴赫');
insert into `music`(id,name,author) values (2,'贝多芬第三钢协','贝多芬');
insert into `music`(id,name,author) values (3,'柴钢协','柴可夫斯基');
insert into `music`(id,name,author) values (4,'柴小协','柴可夫斯基');
insert into `music`(id,name,author) values (5,'图画展览会','穆索尔斯基');
insert into `music`(id,name,author) values (6,'1812序曲','柴可夫斯基');

