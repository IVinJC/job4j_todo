package ru.job4j.todo.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import java.util.List;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class CategoryDbStore {
    private final SessionFactory sf;

    public Category create(Category category) {
        tx(session -> session.save(category));
        return category;
    }

    public List<Category> findAll() {
        return tx(session -> session.createQuery("from ru.job4j.todo.model.Category order by id asc", Category.class)
                .getResultList());
    }

    public Category findById(int id) {
        return tx(session -> session.createQuery("from Category t where t.id = :fId", Category.class)
                .setParameter("fId", id).getSingleResult());
    }

    public Category update(int id, Category category) {
        Category categoryUpdated = tx(session -> session.get(Category.class, id));
        categoryUpdated.setName(category.getName());
        tx(session -> session.merge(category));
        return category;
    }

    public void delete(int id) {
        tx(session -> session.createQuery("DELETE Category t WHERE t.id = :fId")
                .setParameter("fId", id)
                .executeUpdate());
    }

    private <T> T tx(Function<Session, T> command) {
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
