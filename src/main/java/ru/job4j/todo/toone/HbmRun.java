package ru.job4j.todo.toone;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Role role = create(Role.of("ADMIN"), sf);
            create(UserTask.of("Petr Arsentev", role), sf);
            for (UserTask userTask : findAll(UserTask.class, sf)) {
                System.out.println(userTask.getName() + " " + userTask.getRole().getName());
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> T create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(model);
        try {
            session.close();
        } catch (HibernateException e) {
            Throwable ex = new Throwable(e);
            ex.printStackTrace();
        }
        return model;
    }

    public static <T> List<T> findAll(Class<T> cl, SessionFactory sf) throws HibernateException {
        Session session = sf.openSession();
        session.beginTransaction();
        List<T> list = session.createQuery("from " + cl.getName(), cl).list();
        session.close();
        return list;
    }
}