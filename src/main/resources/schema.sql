create table actor (
       id bigint not null auto_increment,
       name varchar(255),
       primary key (id)
) engine=InnoDB;

create table movie (
       id bigint not null auto_increment,
       date date not null,
       rank float not null,
       revenue decimal(19,2),
       title varchar(255) not null,
       primary key (id)
) engine=InnoDB;

create table movie_actor (
       movie_id bigint not null,
       actor_id bigint not null
) engine=InnoDB;

alter table movie_actor
    add constraint FK69qnqd5hnjn2aykvxcj72r9i5
        foreign key (actor_id)
            references actor (id);

alter table movie_actor
    add constraint FKhedvt8u16luotgyoel4fqy7t1
        foreign key (movie_id)
            references movie (id);


INSERT INTO movie (title, date, rank, revenue) VALUES ('Back to the Future', '1985-07-03', 8.5, 2000000);
INSERT INTO movie (title, date, rank, revenue) VALUES ('The Shawshank Redemption', '1994-09-10', 9.3, 25000000);
INSERT INTO movie (title, date, rank, revenue) VALUES ('The Lord of the Rings: The Fellowship of the Ring', '2001-12-21', 8.8, 93000000);
INSERT INTO movie (title, date, rank, revenue) VALUES ('Memento', '2001-06-29', 8.4, 9000000);
INSERT INTO movie (title, date, rank, revenue) VALUES ('Joker', '2019-09-03', 8.4, 55000000);
INSERT INTO movie (title, date, rank, revenue) VALUES ('The Rum Diary', '2011-12-22', 6.1, 45000000);
INSERT INTO movie (title, date, rank, revenue) VALUES ('Deep Water 1', '2012-03-18', 5.4, 480000);
INSERT INTO movie (title, date, rank, revenue) VALUES ('Halo', '2010-08-23', 7.4, 15000000);
INSERT INTO movie (title, date, rank, revenue) VALUES ('Spider-Man: No Way Home', '2021-12-22', 8.1, 35000000);
INSERT INTO movie (title, date, rank, revenue) VALUES ('Game of Thrones', '2011-11-18', 9.2, 12500000);
INSERT INTO movie (title, date, rank, revenue) VALUES ('Almost Genius', '2015-02-28', 1.8, 80000);
INSERT INTO movie (title, date, rank, revenue) VALUES ('Deep Water 2', '2022-03-18', 4.4, 980000);

INSERT INTO actor (name) VALUES ( 'Jack Nicholson');
INSERT INTO actor (name) VALUES ( 'Robert De Niro');
INSERT INTO actor (name) VALUES ( 'Denzel Washington');
INSERT INTO actor (name) VALUES ( 'Harrison Ford');

INSERT INTO movie_actor (movie_id, actor_id) VALUES (1,1);
INSERT INTO movie_actor (movie_id, actor_id) VALUES (1,2);
INSERT INTO movie_actor (movie_id, actor_id) VALUES (2,3);
INSERT INTO movie_actor (movie_id, actor_id) VALUES (3,3);
INSERT INTO movie_actor (movie_id, actor_id) VALUES (4,2);
INSERT INTO movie_actor (movie_id, actor_id) VALUES (5,2);
INSERT INTO movie_actor (movie_id, actor_id) VALUES (6,2);
INSERT INTO movie_actor (movie_id, actor_id) VALUES (7,2);