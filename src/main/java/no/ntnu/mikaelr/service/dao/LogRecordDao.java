package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.entities.*;
import no.ntnu.mikaelr.util.Constants;
import no.ntnu.mikaelr.util.LogRecordType;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class LogRecordDao {

    private static final Logger log = Logger.getLogger(LogRecordDao.class.getName());

    @Autowired
    SessionFactory sessionFactory;

    public List<LogRecord> getLogRecords(int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, userId);

        Query query = session.createQuery("FROM LogRecord lr WHERE user = :user ORDER BY lr.date DESC");
        query.setParameter("user", user);

        @SuppressWarnings("unchecked")
        List<LogRecord> logRecords = query.list();

        Hibernate.initialize(logRecords);

        session.getTransaction().commit();
        session.close();
        return logRecords;
    }

    public void logMissionComplete(Session session, Project project, User user) {
        LogRecord logRecord = new LogRecord();
        logRecord.setDate(Calendar.getInstance().getTime());
        logRecord.setDescription("Du fullførte oppdraget i prosjektet " + project.getName() + ".");
        logRecord.setType(LogRecordType.MISSION);
        logRecord.setGeneratedScore(50);

        logRecord.setUser(user);
        logRecord.setProject(project);

        session.save(logRecord);
    }

    public void logSuggestionPosted(Session session, Suggestion suggestion, User user) {
        LogRecord logRecord = new LogRecord();
        logRecord.setDate(Calendar.getInstance().getTime());
        logRecord.setDescription("Du postet et forslag til prosjektet " + suggestion.getProject().getName() + ": " + suggestion.getTitle());
        logRecord.setType(LogRecordType.SUGGESTION);
        logRecord.setGeneratedScore(Constants.POST_SUGGESTION_SCORE);

        logRecord.setUser(user);
        logRecord.setSuggestion(suggestion);

        session.save(logRecord);
    }

    public void logAgreement(Session session, Suggestion suggestion, User user) {

        Date date = Calendar.getInstance().getTime();

        LogRecord agreeLogRecord = new LogRecord();
        agreeLogRecord.setDate(date);
        agreeLogRecord.setDescription("Du var enig i forslaget " + suggestion.getProject().getName() + ": " + suggestion.getTitle());
        agreeLogRecord.setType(LogRecordType.AGREEMENT);
        agreeLogRecord.setGeneratedScore(Constants.POST_AGREEMENT_SCORE);
        agreeLogRecord.setUser(user);
        agreeLogRecord.setSuggestion(suggestion);
        session.save(agreeLogRecord);

        if (!user.getId().equals(suggestion.getUser().getId())) {
            LogRecord receivedAgreementLogRecord = new LogRecord();
            receivedAgreementLogRecord.setDate(date);
            receivedAgreementLogRecord.setDescription(user.getUsername() + " var enig i ditt forslag " + suggestion.getProject().getName() + ": " + suggestion.getTitle());
            receivedAgreementLogRecord.setType(LogRecordType.SUGGESTION_AGREEMENT);
            receivedAgreementLogRecord.setGeneratedScore(Constants.RECEIVE_AGREEMENT_SCORE);
            receivedAgreementLogRecord.setUser(suggestion.getUser());
            receivedAgreementLogRecord.setSuggestion(suggestion);
            session.save(receivedAgreementLogRecord);
        }
    }

    public void logDisagreement(Session session, Suggestion suggestion, User user) {
        LogRecord logRecord = new LogRecord();
        logRecord.setDate(Calendar.getInstance().getTime());
        logRecord.setDescription("Du var uenig i forslaget " + suggestion.getProject().getName() + ": " + suggestion.getTitle());
        logRecord.setType(LogRecordType.DISAGREEMENT);
        logRecord.setGeneratedScore(Constants.POST_AGREEMENT_SCORE);
        logRecord.setUser(user);
        logRecord.setSuggestion(suggestion);
        session.save(logRecord);
    }

    public void logCommentPosted(Integer suggestionId, Integer userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Suggestion suggestion = session.get(Suggestion.class, suggestionId);
        User user = session.get(User.class, userId);

        LogRecord logRecord = new LogRecord();
        logRecord.setDate(Calendar.getInstance().getTime());
        logRecord.setDescription("Du postet en kommentar til forslaget " + suggestion.getProject().getName() + ": " + suggestion.getTitle());
        logRecord.setType(LogRecordType.COMMENT);
        logRecord.setGeneratedScore(Constants.POST_COMMENT_SCORE);

        logRecord.setUser(user);
        logRecord.setSuggestion(suggestion);

        session.save(logRecord);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteAgreementLogRecord(Session session, Suggestion suggestion, User user) {
        Query query = session.createQuery("from LogRecord where suggestion = :suggestion and user = :user and type = :logType");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);
        query.setParameter("logType", LogRecordType.AGREEMENT);
        LogRecord agreementLogRecord = (LogRecord) query.uniqueResult();
        session.delete(agreementLogRecord);

        if (user.getId() != suggestion.getUser().getId()) {
            query = session.createQuery("from LogRecord where suggestion = :suggestion and user = :user and type = :logType and date = :date");
            query.setParameter("suggestion", suggestion);
            query.setParameter("user", suggestion.getUser());
            query.setParameter("logType", LogRecordType.SUGGESTION_AGREEMENT);
            query.setParameter("date", agreementLogRecord.getDate());
            LogRecord receivedAgreementLogRecord = (LogRecord) query.uniqueResult();
            session.delete(receivedAgreementLogRecord);
        }
    }

    public void deleteDisagreementLogRecord(Session session, Suggestion suggestion, User user) {
        Query query = session.createQuery("delete from LogRecord where suggestion = :suggestion and user = :user and type = :logType");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);
        query.setParameter("logType", LogRecordType.DISAGREEMENT);
        query.executeUpdate();
    }

    public void logAchievement(User user, Achievement achievement) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        LogRecord logRecord = new LogRecord();
        logRecord.setType(LogRecordType.ACHIEVEMENT);
        logRecord.setAchievement(achievement);
        logRecord.setDate(Calendar.getInstance().getTime());
        logRecord.setUser(user);
        logRecord.setDescription("Du mottok utmerkelsen " + achievement.getName());
        logRecord.setGeneratedScore(Constants.GET_ACHIEVEMENT_SCORE);

        session.save(logRecord);
        session.getTransaction().commit();
        session.close();
    }
}
