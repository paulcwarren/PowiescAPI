use PowiesciDB;

drop table if exists `comments`;

create table `comments` (
`id` int(11) auto_increment not null,
`content` mediumtext not null,
`creation_date` datetime null,
`user_id` int(11) not null,
`file_id` int(11) not null,

primary key(`id`),

constraint `FK_USER_COM` foreign key (`user_id`)
references `user`(`id`)

on delete cascade on update cascade,

constraint `FK_FILE_COM` foreign key (`file_id`)
references `files`(`id`)

on delete cascade on update cascade
)engine=InnoDB default charset utf8;