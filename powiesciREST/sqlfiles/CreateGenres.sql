use `PowiesciDB`;
drop table if exists `genres`;
create table genres(
`id` int(11) auto_increment not null,
`name` varchar(20) not null,

primary key(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

drop table if exists `files_genres`;
create table `files_genres`(
`file_id` int(11) not null,
`genre_id` int(11) not null,

primary key(`file_id`,`genre_id`),

  KEY `FK_GENRE_idx` (`genre_id`),
  
  CONSTRAINT `FK_FILE` FOREIGN KEY (`file_id`) 
  REFERENCES `files` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_GENRE` FOREIGN KEY (`genre_id`) 
  REFERENCES `genres` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;