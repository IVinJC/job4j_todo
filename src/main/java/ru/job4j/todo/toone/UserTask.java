package ru.job4j.todo.toone;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "j_user")
public class UserTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public static UserTask of(String name, Role role) {
        UserTask userTask = new UserTask();
        userTask.name = name;
        userTask.role = role;
        return userTask;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserTask userTask = (UserTask) o;
        return id == userTask.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}