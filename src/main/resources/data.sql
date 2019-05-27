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