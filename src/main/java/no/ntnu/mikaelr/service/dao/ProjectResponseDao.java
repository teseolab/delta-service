package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.dto.incoming.ProjectResponseIncoming;
import no.ntnu.mikaelr.model.entities.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

}
