CREATE TABLE if not exists tasks
(
    id          SERIAL PRIMARY KEY,
    name        varchar(250) not null,
    description TEXT        not null,
    created     TIMESTAMP,
    done        BOOLEAN
);

CREATE TABLE if not exists categories
(
    id   SERIAL PRIMARY KEY,
    name varchar(250) not null
);

CREATE TABLE if not exists task_category
(
    id          SERIAL PRIMARY KEY,
    task_id     int not null references tasks (id),
    category_id int not null references categories (id)
);

create table if not exists users
(
    id       SERIAL PRIMARY KEY,
    name     varchar(250),
    email    varchar(250) unique,
    password varchar(250)
);

