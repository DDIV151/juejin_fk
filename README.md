# 前端项目：/w_p


# 后端情况

使用数据库：mysql

````sql
create table article_comment
(
    comment_id      int unsigned auto_increment
        primary key,
    user_id         int unsigned                               not null,
    article_id      int unsigned                               not null,
    comment_content varchar(200)                               not null,
    create_time     datetime         default CURRENT_TIMESTAMP not null,
    update_time     datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    is_delete       tinyint unsigned default '0'               not null
);

create table article_star
(
    user_id     int unsigned                               not null,
    article_id  int unsigned                               not null,
    create_time datetime         default CURRENT_TIMESTAMP not null,
    update_time datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    is_delete   tinyint unsigned default '0'               not null,
    primary key (user_id, article_id)
);

create table juejin_article
(
    article_id      int unsigned auto_increment
        primary key,
    user_id         int unsigned                               not null,
    article_title   varchar(20)      default 'unknown_title'   not null,
    article_content text                                       not null,
    create_time     datetime         default CURRENT_TIMESTAMP not null,
    article_views   bigint unsigned  default '1'               not null,
    is_publish      tinyint unsigned default '0'               not null,
    update_time     datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    is_delete       tinyint unsigned default '0'               not null
);

create table juejin.juejin_user
(
    user_id       int unsigned auto_increment,
    user_name     varchar(20)                                not null,
    user_password char(32)                                   not null,
    user_image    mediumtext                                 null,
    user_resume   varchar(50)                                null,
    create_time   datetime         default CURRENT_TIMESTAMP not null,
    update_time   datetime         default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_delete     tinyint unsigned default '0'               not null,
    primary key (user_id, user_name),
    constraint juejin_user_pk
        unique (user_name)
);
````

