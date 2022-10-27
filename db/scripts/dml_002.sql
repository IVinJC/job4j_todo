alter table tasks add user_id int;
alter table if exists tasks add foreign key (user_id)  references users(id);