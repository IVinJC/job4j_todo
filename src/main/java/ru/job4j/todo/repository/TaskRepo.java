package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TaskRepo {
    private final SessionFactory sf;

    public TaskRepo(SessionFactory sf) {
        this.sf = sf;
    }

    public Task create(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    public List<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> list = session.createQuery("from ru.job4j.todo.model.Task order by id asc", Task.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List<Task> findAllDone() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> done = session.createQuery("from Task t where t.done = :fDone order by id asc", Task.class)
                .setParameter("fDone", true)
                .list();
        session.getTransaction().commit();
        session.close();
        return done;
    }

    public List<Task> findAllNew() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> newList = session.createQuery(
                "from Task t where EXTRACT(EPOCH FROM (:fCreated - t.created)) < 3600 order by id asc", Task.class)
                .setParameter("fCreated", LocalDateTime.now())
                .list();
        session.getTransaction().commit();
        session.close();
        return newList;
    }

    public Task findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task task = session.createQuery("from Task t where t.id = :fId", Task.class)
                .setParameter("fId", id).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return task;
    }

    public Task update(int id, Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task taskUpdated = session.get(Task.class, id);
        taskUpdated.setName(task.getName());
        taskUpdated.setDescription(task.getDescription());
        taskUpdated.setCreated(LocalDateTime.now());
        taskUpdated.setDone(task.isDone());
        session.save(taskUpdated);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    public void delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE Task t WHERE t.id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}