package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TaskService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Task create(Task task) {
        String createdString = task.getCreated().format(formatter);
        LocalDateTime created = LocalDateTime.parse(createdString, formatter);
        task.setCreated(created);
        return taskRepo.create(task);
    }

    public List<Task> findAll() {
        return taskRepo.findAll();
    }

    public List<Task> findAllDone() {
        return taskRepo.findAllDone();
    }

    public List<Task> findAllNew() {
        return taskRepo.findAllNew();
    }

    public Task findById(int id) {
        return taskRepo.findById(id);
    }

    public Task update(int id, Task task) {
        String createdString = task.getCreated().format(formatter);
        LocalDateTime created = LocalDateTime.parse(createdString, formatter);
        task.setCreated(created);
        return taskRepo.update(id, task);
    }

    public void delete(int id) {
        taskRepo.delete(id);
    }
}