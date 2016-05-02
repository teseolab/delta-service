package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.entities.FinishedMission;
import no.ntnu.mikaelr.model.entities.Project;
import no.ntnu.mikaelr.model.entities.Task;
import no.ntnu.mikaelr.model.entities.User;
import no.ntnu.mikaelr.util.Constants;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

        @SuppressWarnings("unchecked")
        List<Project> projects = session.createCriteria(Project.class).list();

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

    public List<Task> getTasks(Integer projectId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Project project = session.get(Project.class, projectId);
        List<Task> tasks = project.getTasks();

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
}
