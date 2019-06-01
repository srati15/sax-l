/* Users table start*/
create table users
(
    user_id    int auto_increment primary key,
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
/* Announcements table end*/

/*QuestionTypes table start*/
create table quetionTypes
(
    question_type_id int auto_increment primary KEY not NULL ,
    question_type varchar(65) not null
);

create unique index quetionTypes_question_type_id_uindex
    on quetionTypes (question_type_id);

create unique index quetionTypes_question_type_uindex
    on quetionTypes (question_type);

/*QuestionTypes table end*/

/*answers table start*/
create table answers
(
    answer_id int auto_increment,
    question_id int not null,
    answer_string varchar(125) not null,
    correctness boolean not null,
    constraint answers_pk
        primary key (answer_id)
);

/*answers table end*/


/*results table start*/
create table results
(
    result_id int auto_increment,
    user_id int not null,
    question_id int not null,
    score int not null,
    constraint results_pk
        primary key (result_id)
);

/*results table end*/
