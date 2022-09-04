package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@Repository
public class TaskDbStore {

    private final SessionFactory sf;

    public TaskDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Task create(Task task) {
        tx(session -> session.save(task));
        return task;
    }

    public List<Task> findAll() {
        return tx(session -> session.createQuery("from ru.job4j.todo.model.Task order by id asc", Task.class)
                .getResultList());
    }

    public List<Task> findAllDone() {
        return tx(session -> session.createQuery("from Task t where t.done = :fDone order by id asc", Task.class)
                .setParameter("fDone", true)
                .list());
    }

    public List<Task> findAllNew() {
        return tx(session -> session.createQuery(
                        "from Task t where EXTRACT(EPOCH FROM (:fCreated - t.created)) < 3600 order by id asc", Task.class)
                .setParameter("fCreated", LocalDateTime.now())
                .list());
    }

    public Task findById(int id) {
        return tx(session -> session.createQuery("from Task t where t.id = :fId", Task.class)
                .setParameter("fId", id).getSingleResult());
    }

    public Task update(int id, Task task) {
        Task taskUpdated = tx(session -> session.get(Task.class, id));
        taskUpdated.setName(task.getName());
        taskUpdated.setDescription(task.getDescription());
        taskUpdated.setCreated(LocalDateTime.now());
        taskUpdated.setDone(task.isDone());
        tx(session -> session.merge(task));
        return task;
    }

    public void delete(int id) {
        tx(session -> session.createQuery("DELETE Task t WHERE t.id = :fId")
                .setParameter("fId", id)
                .executeUpdate());
    }

    public <T> T tx(Function<Session, T> command) {
        var session = sf.openSession();
        try (session) {
            var tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            session.close();
            return rsl;
        } catch (Exception e) {
            var tx = session.getTransaction();
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }
}