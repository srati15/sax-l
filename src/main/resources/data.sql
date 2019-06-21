DROP TABLE if exists users;
DROP TABLE if exists announcements;
DROP TABLE if exists questionTypes;
DROP TABLE if exists question;
DROP TABLE if exists answers;
DROP table if exists quiz;
drop table if exists results;

create table announcements
(
    id                int auto_increment
        primary key,
    announcement_text varchar(255)             not null,
    hyperlink         varchar(255) default '/' null,
    active            tinyint(1)   default 1   null
);

create table answers
(
    id            int auto_increment
        primary key,
    question_id   int          not null,
    answer_string varchar(125) not null
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

create table question
(
    id               int auto_increment
        primary key,
    question_text    varchar(200) not null,
    quiz_id          int          not null,
    question_type_id int          not null
);

create table questionTypes
(
    question_type_id int auto_increment,
    question_type    varchar(65) not null,
    constraint questionTypes_question_type_id_uindex
        unique (question_type_id),
    constraint questionTypes_question_type_uindex
        unique (question_type)
);

alter table questionTypes
    add primary key (question_type_id);

create table quiz
(
    id                       int auto_increment
        primary key,
    quiz_name                varchar(100)                         not null,
    quiz_author_id           int                                  not null,
    date_created             datetime   default CURRENT_TIMESTAMP null,
    randomized               tinyint(1) default 1                 not null,
    is_allowed_practice_mode tinyint(1) default 1                 null,
    is_allowed_correction    tinyint(1) default 1                 null,
    is_single_page           tinyint(1) default 1                 null,
    times_done               int        default 0                 null
);

create table results
(
    result_id   int auto_increment
        primary key,
    user_id     int not null,
    question_id int not null,
    score       int not null
);

create table text_message
(
    id           int auto_increment
        primary key,
    sender_id    int                                not null,
    receiver_id  int                                not null,
    date_sent    datetime default CURRENT_TIMESTAMP null,
    message_sent varchar(1000)                      not null
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

