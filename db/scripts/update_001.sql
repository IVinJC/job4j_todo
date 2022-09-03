CREATE TABLE if not exists task (
                       id SERIAL PRIMARY KEY,
                       name varchar(20) not null ,
                       description TEXT not null ,
                       created TIMESTAMP,
                       done BOOLEAN
);

insert into task(name, description, created, done) values ('name', 'desc', null, false)