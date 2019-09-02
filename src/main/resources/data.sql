use saxl;
create table achievements
(
    id                   int auto_increment
        primary key,
    achievement_name     varchar(255) null,
    achievement_criteria varchar(255) null
);
create table toast
(
    id           int auto_increment
        primary key,
    user_id      int       null,
    toast_text   text      null,
    date_created timestamp null
);


create table activity
(
    id            int auto_increment
        primary key,
    user_id       int                                 null,
    activity_name varchar(255)                        null,
    date_happened timestamp default CURRENT_TIMESTAMP null
);

create table announcements
(
    id                int auto_increment
        primary key,
    user_id           int                      null,
    announcement_text varchar(255)             not null,
    hyperlink         varchar(255) default '/' null,
    active            tinyint(1)   default 1   null
);

create table comment
(
    id           int auto_increment
        primary key,
    quiz_id      int                                 null,
    user_id      int                                 null,
    comment_text text                                null,
    comment_date timestamp default CURRENT_TIMESTAMP null
);

create table friend_requests
(
    id             int auto_increment
        primary key,
    sender_id      int                                 not null,
    receiver_id    int                                 not null,
    request_status int                                 not null,
    date_sent      timestamp default CURRENT_TIMESTAMP not null
);

create table inbox
(
    id              int auto_increment
        primary key,
    first_name      varchar(255)         null,
    sender_mail     varchar(255)         null,
    message_subject varchar(255)         null,
    message_text    text                 null,
    seen            tinyint(1) default 0 null,
    date_sent       timestamp            null
);

create table reply_message
(
    id         int auto_increment
        primary key,
    message_id int                                 null,
    reply_text text                                null,
    date_sent  timestamp default CURRENT_TIMESTAMP null
);

create table text_message
(
    id           int auto_increment
        primary key,
    sender_id    int                                  not null,
    receiver_id  int                                  not null,
    date_sent    datetime   default CURRENT_TIMESTAMP null,
    message_sent varchar(1000)                        not null,
    seen         tinyint(1) default 0                 null
);

create table user_achievements
(
    id             int auto_increment
        primary key,
    user_id        int null,
    achievement_id int null
);

create table users
(
    id         int auto_increment
        primary key,
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
    on users (id);

insert into achievements (achievement_name, achievement_criteria) VALUES ('Amateur Author', 'Gained for creating 1 quiz');
insert into achievements (achievement_name, achievement_criteria) VALUES ('Prolific Author', 'Gained for creating 5 quizzes');
insert into achievements (achievement_name, achievement_criteria) VALUES ('Prodigious Author', 'Gained for creating 10 quizzes');
insert into achievements (achievement_name, achievement_criteria) VALUES ('Quiz Machine', 'Gained for taking 10 quizzes');
insert into achievements (achievement_name, achievement_criteria) VALUES ('I Am The Greatest', 'Gained for getting highest score in a quiz');