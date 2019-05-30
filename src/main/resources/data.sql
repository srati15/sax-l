/* Users table start*/
create table users
(
    user_id    int auto_increment,
    user_name  varchar(50)  not null,
    pass       varchar(255) not null,
    mail       varchar(255) not null,
    last_name  varchar(50)  not null,
    user_type  int          not null,
    first_name varchar(50)  not null,
    constraint user_user_name_uindex
        unique (user_name)
);

create index user_id_index
    on users (user_id);
/* Users table end*/

/* Announcements table start*/
create table announcements
(
    id                int auto_increment
        primary key,
    announcement_text varchar(255)             not null,
    hyperlink         varchar(255) default '/' null,
    active            tinyint(1)   default 1   null
);
/* Announcements table start*/
