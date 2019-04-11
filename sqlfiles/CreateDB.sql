drop database if exists `PowiescDoSukcesuDB`;

create database `PowiescDoSukcesuDB`
    character set `utf8`
    collate `utf8_polish_ci`;

use `PowiescDoSukcesuDB`;

create table `users`
(
    `id`         mediumint unsigned     not null auto_increment,
    `username`   varchar(50)            not null unique,
    `password`   char(80)               not null,
    `email`      varchar(50)            not null unique,
    `first_name` varchar(50),
    `last_name`  varchar(50),
    `sex`        enum ('male','female') not null,
    `image`      mediumblob,

    primary key (`id`)
) engine = InnoDB;

create table `roles`
(
    `id`   tinyint unsigned not null auto_increment,
    `name` varchar(20)      not null unique,

    primary key (`id`)
) engine = InnoDB;

create table `genres`
(
    `id`   tinyint unsigned not null auto_increment,
    `name` varchar(15)      not null unique,

    primary key (`id`)

) engine = InnoDB;

create table `books`
(
    `id`           mediumint unsigned not null auto_increment,
    `title`        varchar(50)        not null unique,
    `file`         longblob           not null,
    `user_id`      mediumint unsigned,
    `image`        mediumblob,
    `created_date` datetime           not null,
    `rating`       double default 0,
    `description`  varchar(100),

    primary key (`id`),
    constraint `FK_BOOK_USER`
        foreign key (`user_id`)
            references `users` (`id`)
            ON DELETE SET NULL ON UPDATE NO ACTION
) engine = InnoDB;

create table `comments`
(
    `id`           int unsigned       not null auto_increment,
    `content`      varchar(300)       not null,
    `book_id`      mediumint unsigned not null,
    `user_id`      mediumint unsigned,
    `created_date` datetime           not null,

    primary key (`id`),

    constraint `FK_COMMENT_USER`
        foreign key (`user_id`)
            references `users` (`id`)
            ON DELETE SET NULL ON UPDATE NO ACTION,

    constraint `FK_COMMENT_BOOK`
        foreign key (`book_id`)
            references `books` (`id`)
            ON DELETE CASCADE ON UPDATE NO ACTION
) engine = InnoDB;

create table `books_genres`
(
    `book_id`  mediumint unsigned not null,
    `genre_id` tinyint unsigned   not null,

    primary key (`book_id`, `genre_id`),

    constraint `FK_GENRES_BOOK`
        foreign key (`book_id`)
            references `books` (`id`)
            ON DELETE CASCADE ON UPDATE NO ACTION,

    constraint `FK_GENRES_USER`
        foreign key (`genre_id`)
            references `genres` (`id`)
            ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB;

create table `books_votes`
(

    `id`           int unsigned       not null auto_increment,
    `book_id`      mediumint unsigned not null,
    `user_id`      mediumint unsigned not null,
    `rating`       double             not null,
    `added_date`   datetime,
    `changed_date` datetime,

    primary key (`id`),

    constraint `FK_VOTES_BOOK`
        foreign key (`book_id`)
            references `books` (`id`)
            ON DELETE CASCADE ON UPDATE NO ACTION,

    constraint `FK_VOTES_USER`
        foreign key (`user_id`)
            references `users` (`id`)
            ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB;

create table `users_roles`
(
    `user_id` mediumint unsigned not null,
    `role_id` tinyint unsigned   not null,

    primary key (`user_id`, `role_id`),

    constraint `FK_ROLES_USER`
        foreign key (`user_id`)
            references `users` (`id`)
            ON DELETE CASCADE ON UPDATE NO ACTION,

    constraint `FK_ROLES_ROLE`
        foreign key (`role_id`)
            references `roles` (`id`)
            ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB;

insert into `roles`(`name`)
values ('ROLE_NORMAL_USER'),
       ('ROLE_PREMIUM_USER'),
       ('ROLE_ADMIN');

create unique index `idx_vote_user_book`
    on `books_votes` (`user_id`, `book_id`);



set foreign_key_checks = 1;