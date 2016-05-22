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

    @Autowired
    private AchievementDao achievementDao;

    public List<Suggestion> getSuggestions(Integer projectId) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Project project = session.get(Project.class, projectId);

        Query query = session.createQuery("from Suggestion where project = :project order by date desc");
        query.setParameter("project", project);

        @SuppressWarnings("unchecked")
        List<Suggestion> suggestions = query.list();

        if (suggestions.size() > 0) {
            Hibernate.initialize(suggestions);
        }

        session.getTransaction().commit();
        session.close();

        return suggestions;

    }

    public MultiValueMap<String, String> postAgreement(Integer suggestionId, Integer userId) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Suggestion suggestion = session.get(Suggestion.class, suggestionId);
        User user = session.get(User.class, userId);

        if (deleteDisagreement(session, suggestion, user)) {
            logRecordDao.deleteDisagreementLogRecord(session, suggestion, user);
            user.decrementScore(Constants.POST_AGREEMENT_SCORE);
        }

        Agreement existingAgreement = getAgreement(session, suggestion, user);
        if (existingAgreement == null) {
            Agreement agreement = new Agreement(suggestion, user);
            logRecordDao.logAgreement(session, suggestion, user);
            user.incrementScore(Constants.POST_AGREEMENT_SCORE);
            suggestion.getUser().incrementScore(Constants.RECEIVE_AGREEMENT_SCORE);
            session.save(agreement);
            session.update(suggestion.getUser());
        }

        session.save(user);
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        suggestion = session.get(Suggestion.class, suggestionId);
        Integer numberOfAgreements = suggestion.getAgreements().size();
        Integer numberOfDisagreements = suggestion.getDisagreements().size();
        session.close();

        if (numberOfAgreements == 5) {
            achievementDao.addAgreementAchievement(suggestion.getUser());
        }

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

        if (deleteAgreement(session, suggestion, user)) {
            logRecordDao.deleteAgreementLogRecord(session, suggestion, user);
            user.decrementScore(Constants.POST_AGREEMENT_SCORE);
            suggestion.getUser().decrementScore(Constants.RECEIVE_AGREEMENT_SCORE);
            session.update(suggestion.getUser());
        }

        Disagreement existingDisagreement = getDisagreement(session, suggestion, user);
        if (existingDisagreement == null) {
            Disagreement disagreement = new Disagreement(suggestion, user);
            logRecordDao.logDisagreement(session, suggestion, user);
            user.incrementScore(Constants.POST_AGREEMENT_SCORE);
            session.save(disagreement);
        }

        session.save(user);
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

    private boolean deleteAgreement(Session session, Suggestion suggestion, User user) {
        Query query = session.createQuery("delete from Agreement where suggestion = :suggestion and user = :user");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);
        int rowsDeleted = query.executeUpdate();
        return rowsDeleted > 0;
    }

    private boolean deleteDisagreement(Session session, Suggestion suggestion, User user) {
        Query query = session.createQuery("delete from Disagreement where suggestion = :suggestion and user = :user");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);
        int rowsDeleted = query.executeUpdate();
        return rowsDeleted > 0;
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

        Query query = session.createQuery("from Comment where suggestion = :suggestion order by date desc");
        query.setParameter("suggestion", suggestion);

        @SuppressWarnings("unchecked")
        List<Comment> comments = query.list();

        if (comments.size() > 0) {
            Hibernate.initialize(comments);
        }

        session.getTransaction().commit();
        session.close();

        return comments;

    }

    public List<Comment> postComment(Integer suggestionId, Integer userId, String commentText) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Suggestion suggestion = session.get(Suggestion.class, suggestionId);
        User user = session.get(User.class, userId);
        user.incrementScore(Constants.POST_COMMENT_SCORE);

        Comment comment = new Comment();
        comment.setDate(Calendar.getInstance().getTime());
        comment.setComment(commentText);
        comment.setSuggestion(suggestion);
        comment.setUser(user);

        session.save(comment);
        session.save(user);
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
        user.incrementScore(Constants.POST_SUGGESTION_SCORE);

        logRecordDao.logSuggestionPosted(session, suggestion, user);

        session.save(suggestion);
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return suggestion;
    }

}
