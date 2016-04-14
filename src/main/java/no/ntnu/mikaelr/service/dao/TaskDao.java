package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.entities.Task;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskDao {

    @Autowired
    SessionFactory sessionFactory;

    public Task getTask(Integer taskId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Task task = session.get(Task.class, taskId);

        if (task != null) {
            Hibernate.initialize(task);
        }

        session.getTransaction().commit();
        session.close();
        return task;
    }

}
