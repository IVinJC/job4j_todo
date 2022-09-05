CREATE TABLE if not exists categories
(
    id   SERIAL PRIMARY KEY,
    name varchar(20) not null
);

CREATE TABLE if not exists task_category
(
    id          SERIAL PRIMARY KEY,
    task_id     int not null references tasks (id),
    category_id int not null references categories (id)
);

