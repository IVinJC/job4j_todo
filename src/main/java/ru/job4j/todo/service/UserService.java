package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserDbStore;

import java.util.Optional;

@Service
public class UserService {
    private final UserDbStore userDbStore;

    public UserService(UserDbStore userDbStore) {
        this.userDbStore = userDbStore;
    }

    public Optional<User> create(User user) {
        return userDbStore.create(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userDbStore.findByEmail(email);
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return userDbStore.findUserByEmailAndPwd(email, password);
    }
}
