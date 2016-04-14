package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.dto.incoming.ProjectResponseIncoming;
import no.ntnu.mikaelr.model.entities.*;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProjectResponseDao {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    UserDao userDao;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    TaskDao taskDao;

    public ProjectResponse createProjectResponse(ProjectResponseIncoming incoming) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String[] response = incoming.getResponse();
        User user = userDao.getUserById(incoming.getUserId());
        Project project = projectDao.getProject(incoming.getProjectId());
        Task task = taskDao.getTask(incoming.getTaskId());

        ProjectResponse projectResponse = new ProjectResponse(response, user, project, task);

        if (project != null) {
            Hibernate.initialize(projectResponse);
        }

        session.save(projectResponse);
        session.getTransaction().commit();
        session.close();

        return projectResponse;
    }

    public boolean missionForProjectIsCompletedByUser(Integer projectId, Integer userId) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Project project = projectDao.getProject(projectId);
        User user = userDao.getUserById(userId);

        Query query = session.createQuery("select count(pr) from ProjectResponse pr where project = :project and user = :user");
        query.setParameter("project", project);
        query.setParameter("user", user);

        Long numberOfTasksCompleted = (Long) query.uniqueResult();
        int numberOfTasksForProject = project.getTasks().size();

        session.close();

        return numberOfTasksCompleted >= numberOfTasksForProject;
    }

}