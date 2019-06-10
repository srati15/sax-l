DROP TABLE if exists users;
DROP TABLE if exists announcements;
DROP TABLE if exists questionTypes;
DROP TABLE if exists question;
DROP TABLE if exists answers;
DROP table if exists quiz;
drop table if exists results;

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
create table questionTypes
(
    question_type_id int auto_increment primary KEY not NULL ,
    question_type varchar(65) not null
);

create unique index questionTypes_question_type_id_uindex
    on questionTypes (question_type_id);

create unique index questionTypes_question_type_uindex
    on questionTypes (question_type);

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
-- creating question table
create table question
(
    question_id      int auto_increment
        primary key,
    question_text    varchar(200) not null,
    quiz_id          int          not null,
    question_type_id int          not null
 #   constraint question_questionypes_question_type_id_fk
 #       foreign key (question_type_id) references questionTypes (question_type_id)
#    constraint question_quiz_quiz_id_fk
#         foreign key (quiz_id) references quiz (quiz_id)
);


-- creating quiz table
create table quiz(
                     quiz_id    int auto_increment primary key,
                     quiz_name  varchar(100)  not null,
                     quiz_author varchar(100) not null,
                     date_created datetime default CURRENT_TIMESTAMP

);
/*friend requests table start*/
create table friend_requests
(
    id int auto_increment,
    sender_id int not null,
    receiver_id int not null,
    request_status int not null,
    send_date timestamp default now() not null,
    constraint friend_requests_pk
        primary key (id)
);
/*friend requests table end*/

