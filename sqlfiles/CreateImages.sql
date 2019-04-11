use `PowiesciDB`;
create table `images`(
	`id` int(11) not null auto_increment,
    `name` varchar(30) not null,
    `image` blob not null,
    
    primary key(`id`)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1;
