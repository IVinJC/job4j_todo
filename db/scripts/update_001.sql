CREATE TABLE if not exists task (
                       id SERIAL PRIMARY KEY,
                       name varchar(20) not null ,
                       description TEXT not null ,
                       created TIMESTAMP,
                       done BOOLEAN
);

create table if not exists users(
                                    id SERIAL PRIMARY KEY,
                                    name varchar(30),
                                    email varchar(250) unique ,
                                    password varchar(250)
);
