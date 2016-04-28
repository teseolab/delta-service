package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.dto.incoming.SuggestionIn;
import no.ntnu.mikaelr.model.entities.*;
import no.ntnu.mikaelr.security.SessionUser;
import no.ntnu.mikaelr.util.Constants;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SuggestionDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private LogRecordDao logRecordDao;

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

        Collections.reverse(suggestions);
        return suggestions;

    }

    public MultiValueMap<String, String> postAgreement(Integer suggestionId, Integer userId) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Suggestion suggestion = session.get(Suggestion.class, suggestionId);
        User user = session.get(User.class, userId);

        deleteDisagreement(session, suggestion, user);
        logRecordDao.deleteDisagreementLogRecord(session, suggestion, user);

        Agreement existingAgreement = getAgreement(session, suggestion, user);
        if (existingAgreement == null) {
            Agreement agreement = new Agreement(suggestion, user);
            logRecordDao.logAgreement(session, suggestion, user);
            session.save(agreement);
        }

        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        suggestion = session.get(Suggestion.class, suggestionId);
        Integer numberOfAgreements = suggestion.getAgreements().size();
        Integer numberOfDisagreements = suggestion.getDisagreements().size();
        session.close();

        MultiValueMap<String, String> result = new LinkedMultiValueMap<String, String>();
        result.add("agreements", numberOfAgreements.toString());
        result.add("disagreements", numberOfDisagreements.toString());

        return result;
    }

    public MultiValueMap<String, String> postDisagreement(Integer suggestionId, Integer userId) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Suggestion suggestion = session.get(Suggestion.class, suggestionId);
        User user = session.get(User.class, userId);

        deleteAgreement(session, suggestion, user);
        logRecordDao.deleteAgreementLogRecord(session, suggestion, user);

        Disagreement existingDisagreement = getDisagreement(session, suggestion, user);
        if (existingDisagreement == null) {
            Disagreement disagreement = new Disagreement(suggestion, user);
            logRecordDao.logDisagreement(session, suggestion, user);
            session.save(disagreement);
        }

        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        suggestion = session.get(Suggestion.class, suggestionId);
        Integer numberOfAgreements = suggestion.getAgreements().size();
        Integer numberOfDisagreements = suggestion.getDisagreements().size();
        session.close();

        MultiValueMap<String, String> result = new LinkedMultiValueMap<String, String>();
        result.add("agreements", numberOfAgreements.toString());
        result.add("disagreements", numberOfDisagreements.toString());

        return result;
    }

    private void deleteAgreement(Session session, Suggestion suggestion, User user) {
        Query query = session.createQuery("delete from Agreement where suggestion = :suggestion and user = :user");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);
        int rowsDeleted = query.executeUpdate();
    }

    private void deleteDisagreement(Session session, Suggestion suggestion, User user) {
        Query query = session.createQuery("delete from Disagreement where suggestion = :suggestion and user = :user");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);
        int rowsDeleted = query.executeUpdate();
    }

    private Agreement getAgreement(Session session, Suggestion suggestion, User user) {

        Query query = session.createQuery("from Agreement a where suggestion = :suggestion and user = :user");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);

        return (Agreement) query.uniqueResult();
    }

    private Disagreement getDisagreement(Session session, Suggestion suggestion, User user) {

        Query query = session.createQuery("from Disagreement a where suggestion = :suggestion and user = :user");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);

        return (Disagreement) query.uniqueResult();
    }

    public String userAgrees(Suggestion suggestion, User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String result;
        Query query = session.createQuery("select count(a) from Agreement a where suggestion = :suggestion and user = :user");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);
        boolean agree = (Long) query.uniqueResult() >= 1;

        query = session.createQuery("select count(da) from Disagreement da where suggestion = :suggestion and user = :user");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);
        boolean disagree = (Long) query.uniqueResult() >= 1;

        if (agree)
            result = Constants.YES;
        else if (disagree)
            result = Constants.NO;
        else
            result = Constants.NA;

        session.getTransaction().commit();
        session.close();

        return result;
    }

    public List<Comment> getComments(Integer suggestionId) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Suggestion suggestion = session.get(Suggestion.class, suggestionId);
        List<Comment> comments = suggestion.getComments();

        if (comments.size() > 0) {
            Hibernate.initialize(comments);
        }

        session.getTransaction().commit();
        session.close();

        Collections.reverse(comments);
        return comments;

    }

    public List<Comment> postComment(Integer suggestionId, Integer userId, String commentText) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Suggestion suggestion = session.get(Suggestion.class, suggestionId);
        User user = session.get(User.class, userId);

        Comment comment = new Comment();
        comment.setDate(Calendar.getInstance().getTime());
        comment.setComment(commentText);
        comment.setSuggestion(suggestion);
        comment.setUser(user);

        session.save(comment);
        session.getTransaction().commit();
        session.close();

        return getComments(suggestionId);
    }

    public Suggestion createSuggestion(SuggestionIn incoming) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String title = incoming.getTitle();
        String details = incoming.getDetails();
        Date date = incoming.getDate();
        String imageUri = incoming.getImageUri();
        int projectId = incoming.getProjectId();

        Suggestion suggestion = new Suggestion(title, details, date, imageUri);

        Project project = session.get(Project.class, projectId);
        suggestion.setProject(project);

        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        User user = session.get(User.class, userId);
        suggestion.setUser(user);

        logRecordDao.logSuggestionPosted(session, suggestion, user);

        session.save(suggestion);
        session.getTransaction().commit();
        session.close();
        return suggestion;

    }
}
