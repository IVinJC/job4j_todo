package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskDbStore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TaskService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final TaskDbStore taskDbStore;

    public TaskService(TaskDbStore taskDbStore) {
        this.taskDbStore = taskDbStore;
    }

    public Task create(Task task) {
        String createdString = task.getCreated().format(formatter);
        LocalDateTime created = LocalDateTime.parse(createdString, formatter);
        task.setCreated(created);
        return taskDbStore.create(task);
    }

    public List<Task> findAll() {
        return taskDbStore.findAll();
    }

    public List<Task> findAllDone() {
        return taskDbStore.findAllDone();
    }

    public List<Task> findAllNew() {
        return taskDbStore.findAllNew();
    }

    public Task findById(int id) {
        return taskDbStore.findById(id);
    }

    public Task update(int id, Task task) {
        String createdString = task.getCreated().format(formatter);
        LocalDateTime created = LocalDateTime.parse(createdString, formatter);
        task.setCreated(created);
        return taskDbStore.update(id, task);
    }

    public void delete(int id) {
        taskDbStore.delete(id);
    }
}