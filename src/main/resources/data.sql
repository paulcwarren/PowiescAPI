insert into genres(name)
values ("Comedy"),
       ("Drama"),
       ("Romance"),
       ("Tragedy"),
       ("Lyric"),
       ("Historical"),
       ("Thriller");


insert into roles(name)
values ("ROLE_NORMAL_USER"),
       ("ROLE_ADMIN"),
       ("ROLE_PREMIUM_USER");

insert into users(username, password, email, first_name, last_name, sex)
values ("JankDev", "$2a$04$TU.poYw2aSTDEMeHONUBhe9gI6T/AfAthcZH/7XAfORf946dmtX7m", test@dom.pl, NULL, NULL, MALE),
       ("MariaN", "$2a$04$eetW64PqqzqRHrfVJebuDuUCMYrS1Ub9CVTtMQd7rUhRb4zYg9qyi", mariaN@dom.pl, "Maria", NULL, FEMALE),
       ("KorNet", "$2a$04$WRXe5ijaiEzK7kRGrjD6IODUJMRjW4UduoaGWFQH0dn0eePIoDqzO", test2@dom.pl, NULL, NULL, FEMALE),
       ("JohnDoe", "$2a$04$eetW64PqqzqRHrfVJebuDuUCMYrS1Ub9CVTtMQd7rUhRb4zYg9qyi", john@dom.pl, "John", "Doe", MALE);

insert into books(title, file, user_id, description)
values ("Demo1", RAWTOHEX("file1"), 1, NULL),
       ("Demo2", RAWTOHEX("file2"), 2, "New Times"),
       ("Title", RAWTOHEX("file3"), 1, NULL),
       ("Book", RAWTOHEX("file1"), 4, NULL),
       ("Hello", RAWTOHEX("file2"), 2, "New Times"),
       ("Hey Hello", RAWTOHEX("file3"), 3, NULL),
       ("Demo6", RAWTOHEX("file1"), 3, NULL),
       ("Test", RAWTOHEX("file2"), 2, "New Times"),
       ("Hi There", RAWTOHEX("file3"), 4, NULL);

