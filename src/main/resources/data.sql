insert into genres(name)
values ('Comedy'),
       ('Drama'),
       ('Romance'),
       ('Tragedy'),
       ('Lyric'),
       ('Historical'),
       ('Thriller');

insert into roles(name)
values ('ROLE_ADMIN'),
       ('ROLE_PREMIUM_USER');

insert into users(username, password, email, first_name, last_name, sex)
values ('JankDev', '$2a$04$TU.poYw2aSTDEMeHONUBhe9gI6T/AfAthcZH/7XAfORf946dmtX7m', 'test@dom.pl', NULL, NULL, 'MALE'),
       ('MariaN', '$2a$04$eetW64PqqzqRHrfVJebuDuUCMYrS1Ub9CVTtMQd7rUhRb4zYg9qyi', 'mariaN@dom.pl', 'Maria', NULL,
        'FEMALE'),
       ('KorNet', '$2a$04$WRXe5ijaiEzK7kRGrjD6IODUJMRjW4UduoaGWFQH0dn0eePIoDqzO', 'test2@dom.pl', NULL, NULL, 'FEMALE'),
       ('JohnDoe', '$2a$04$eetW64PqqzqRHrfVJebuDuUCMYrS1Ub9CVTtMQd7rUhRb4zYg9qyi', 'john@dom.pl', 'John', 'Doe',
        'MALE');

insert into books(title, user_id, description)
values ('Demo1', 1, NULL),
       ('Demo2', 2, 'New Times'),
       ('Title', 1, NULL),
       ('Book', 4, NULL),
       ('Hello', 2, 'New Times'),
       ('Hey Hello', 3, NULL),
       ('Demo6', 3, NULL),
       ('Test', 2, 'New Times'),
       ('Hi There', 4, NULL);

insert into books_genres(book_id, genre_id)
values (1, 1),
       (1, 3),
       (1, 7),
       (1, 5),
       (2, 3),
       (2, 5),
       (2, 7),
       (2, 4),
       (3, 2),
       (3, 1),
       (4, 6),
       (4, 2),
       (5, 4),
       (6, 1),
       (7, 3),
       (7, 4),
       (7, 6),
       (8, 6),
       (8, 2),
       (9, 1),
       (9, 6);
