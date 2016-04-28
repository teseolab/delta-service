package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.entities.LogRecord;
import no.ntnu.mikaelr.model.entities.Project;
import no.ntnu.mikaelr.model.entities.Suggestion;
import no.ntnu.mikaelr.model.entities.User;
import no.ntnu.mikaelr.util.LogRecordType;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;

public class LogRecordDao {

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
        logRecord.setGeneratedScore(25);

        logRecord.setUser(user);
        logRecord.setSuggestion(suggestion);

        session.save(logRecord);
    }

    public void logAgreement(Session session, Suggestion suggestion, User user) {
        LogRecord logRecord = new LogRecord();
        logRecord.setDate(Calendar.getInstance().getTime());
        logRecord.setDescription("Du var enig i forslaget " + suggestion.getProject().getName() + ": " + suggestion.getTitle());
        logRecord.setType(LogRecordType.AGREEMENT);
        logRecord.setGeneratedScore(10);
        logRecord.setUser(user);
        logRecord.setSuggestion(suggestion);
        session.save(logRecord);
    }

    public void logDisagreement(Session session, Suggestion suggestion, User user) {
        LogRecord logRecord = new LogRecord();
        logRecord.setDate(Calendar.getInstance().getTime());
        logRecord.setDescription("Du var uenig i forslaget " + suggestion.getProject().getName() + ": " + suggestion.getTitle());
        logRecord.setType(LogRecordType.DISAGREEMENT);
        logRecord.setGeneratedScore(10);
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
        logRecord.setGeneratedScore(10);

        logRecord.setUser(user);
        logRecord.setSuggestion(suggestion);

        session.save(logRecord);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteAgreementLogRecord(Session session, Suggestion suggestion, User user) {
        Query query = session.createQuery("delete from LogRecord where suggestion = :suggestion and user = :user and type = :logType");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);
        query.setParameter("logType", LogRecordType.AGREEMENT);
        int recordsDeleted = query.executeUpdate();
    }

    public void deleteDisagreementLogRecord(Session session, Suggestion suggestion, User user) {
        Query query = session.createQuery("delete from LogRecord where suggestion = :suggestion and user = :user and type = :logType");
        query.setParameter("suggestion", suggestion);
        query.setParameter("user", user);
        query.setParameter("logType", LogRecordType.DISAGREEMENT);
        int recordsDeleted = query.executeUpdate();
    }

}
