package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.entities.*;
import no.ntnu.mikaelr.util.Constants;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Table;
import java.util.List;
import java.util.Set;

public class ProjectDao {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    private LogRecordDao logRecordDao;

    public List<Project> getProjects() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Project order by id");
        @SuppressWarnings("unchecked")
        List<Project> projects = query.list();

        session.getTransaction().commit();
        session.close();
        return projects;
    }

    public Project getProject(Integer projectId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Project project = session.get(Project.class, projectId);

        if (project != null) {
            Hibernate.initialize(project.getTasks());
        }

        session.getTransaction().commit();
        session.close();
        return project;
    }

    public List<Task> getTasks(Project project) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Task where project = :project order by taskOrder");
        query.setParameter("project", project);
        @SuppressWarnings("unchecked")
        List<Task> tasks = query.list();

        if (tasks.size() > 0) {
            Hibernate.initialize(tasks);
        }

        session.getTransaction().commit();
        session.close();
        return tasks;
    }

    public void setMissionComplete(Integer projectId, Integer userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Project project = session.get(Project.class, projectId);
        User user = session.get(User.class, userId);

        FinishedMission finishedMission = new FinishedMission();
        finishedMission.setProject(project);
        finishedMission.setUser(user);

        user.incrementScore(Constants.FINISH_MISSION_SCORE);

        logRecordDao.logMissionComplete(session, project, user);

        session.save(finishedMission);
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteTasks(Integer projectId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Project project = session.get(Project.class, projectId);

        Query query = session.createQuery("from Task where project = :project");
        query.setParameter("project", project);
        List<Task> tasks = query.list();

        for (Task task : tasks) {
            session.delete(task);
        }

        session.getTransaction().commit();
        session.close();
    }
}
