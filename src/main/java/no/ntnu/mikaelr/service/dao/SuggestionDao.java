package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.entities.Project;
import no.ntnu.mikaelr.model.entities.Suggestion;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SuggestionDao {

    @Autowired
    SessionFactory sessionFactory;

    public List<Suggestion> getSuggestions(Integer projectId) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Project project = session.get(Project.class, projectId);
        List<Suggestion> suggestions = project.getSuggestions();

        if (suggestions.size() > 0) {
            Hibernate.initialize(suggestions);
        }

        session.getTransaction().commit();
        session.close();
        return suggestions;

    }

}
