package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.entities.Task;
import no.ntnu.mikaelr.model.entities.TaskQuestion;
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

    public TaskQuestion getTaskQuestion(Integer questionId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        TaskQuestion taskQuestion = session.get(TaskQuestion.class, questionId);

        session.getTransaction().commit();
        session.close();
        return taskQuestion;
    }
}
