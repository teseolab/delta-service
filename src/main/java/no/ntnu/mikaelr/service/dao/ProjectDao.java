package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.entities.Project;
import no.ntnu.mikaelr.model.entities.Task;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class ProjectDao {

    @Autowired
    SessionFactory sessionFactory;

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

        session.getTransaction().commit();
        session.close();
        return project;
    }

    public Set<Task> getTasks(Integer projectId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Project project = session.get(Project.class, projectId);
        Set<Task> tasks = project.getTasks();

        if (tasks.size() > 0) {
            Hibernate.initialize(tasks);
        }

        session.getTransaction().commit();
        session.close();
        return tasks;
    }

}
