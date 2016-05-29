package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.dto.incoming.TaskResponseIn;
import no.ntnu.mikaelr.model.entities.*;
import no.ntnu.mikaelr.security.SessionUser;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public TaskResponse createProjectResponse(TaskResponseIn incoming) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<String> response = incoming.getResponse();
        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        User user = userDao.getUserById(userId);
        TaskQuestion question = taskDao.getTaskQuestion(incoming.getQuestionId());

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setResponse(response);
        taskResponse.setUser(user);
        taskResponse.setQuestion(question);

        Hibernate.initialize(taskResponse);

        session.save(taskResponse);
        session.getTransaction().commit();
        session.close();

        return taskResponse;
    }

    public boolean missionForProjectIsCompletedByUser(Integer projectId) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Project project = projectDao.getProject(projectId);
        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        User user = userDao.getUserById(userId);

        Query query = session.createQuery("select count(fm) from FinishedMission fm where project = :project and user = :user");
        query.setParameter("project", project);
        query.setParameter("user", user);

        Long numberOfTasksCompleted = (Long) query.uniqueResult();
        int numberOfTasksForProject = project.getTasks().size();

        session.close();

        return numberOfTasksCompleted >= numberOfTasksForProject;
    }

    public boolean questionIsAnswered(User user, TaskQuestion question) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("select count(tr) from TaskResponse tr where user =:user and question = :question");
        query.setParameter("user", user);
        query.setParameter("question", question);

        boolean taskIsFinished = (Long) query.uniqueResult() > 0;
        session.close();
        return taskIsFinished;
    }
}
