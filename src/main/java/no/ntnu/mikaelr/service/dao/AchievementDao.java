package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.entities.Achievement;
import no.ntnu.mikaelr.model.entities.User;
import no.ntnu.mikaelr.model.entities.UserAchievement;
import no.ntnu.mikaelr.util.Constants;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AchievementDao {

    private static final Logger log = Logger.getLogger(AchievementDao.class.getName());

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    LogRecordDao logRecordDao;

    @Autowired
    private UserDao userDao;

    public Achievement getAchievement(int achievementId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Achievement achievement = session.get(Achievement.class, achievementId);
        session.getTransaction().commit();
        session.close();
        return achievement;
    }

    private UserAchievement getUserAchievement(Session session, Achievement achievement, User user) {
        Query query = session.createQuery("from UserAchievement where achievement = :achievement and user = :user");
        query.setParameter("achievement", achievement);
        query.setParameter("user", user);
        return (UserAchievement) query.uniqueResult();
    }

    public Achievement addUploadedAvatarAchievement(int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Achievement avatarAchievement = null;
        User user = userDao.getUserById(userId);

        Query query = session.createQuery("select count(ua) from UserAchievement ua where user = :user " +
                "and achievement.acgievementId = :achievementId");
        query.setParameter("user", user);
        query.setParameter("achievementId", Constants.ACHIEVEMENT_PROFILBILDE);
        boolean shouldAddAchievement = (Long) query.uniqueResult() == 0;
        if (shouldAddAchievement) {
            avatarAchievement = session.get(Achievement.class, Constants.ACHIEVEMENT_PROFILBILDE);
            if (avatarAchievement != null) {
                createUserAchievement(user, session, avatarAchievement);
            } else {
                log.log(Level.WARNING, "Achievement with id " + Constants.ACHIEVEMENT_PROFILBILDE + " was not found, " +
                        "and could not be added to user " + user.getUsername() + " with id " + user.getId());
            }
        }

        session.getTransaction().commit();
        session.close();
        return avatarAchievement;
    }

    public void addSuggestionAchievement(int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        int suggestionCount = userDao.getNumberOfSuggestionsPosted(userId);
        if (suggestionCount == 1) {
            Achievement achievement = getAchievement(Constants.ACHIEVEMENT_DELTAKER_V2);
            User user = userDao.getUserById(userId);
            if (achievement != null) {
                createUserAchievement(user, session, achievement);
            } else {
                log.log(Level.WARNING, "Achievement with id " + Constants.ACHIEVEMENT_DELTAKER_V2 + " was not found, " +
                        "and could not be added to user " + user.getUsername() + " with id " + user.getId());
            }
        }

        session.getTransaction().commit();
        session.close();
    }

    public void addCommentAchievement(int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        int commentCount = userDao.getNumberOfCommentsPosted(userId);
        if (commentCount == 5) {
            Achievement achievement = getAchievement(Constants.ACHIEVEMENT_KOMMENTARER_V1);
            User user = userDao.getUserById(userId);
            if (achievement != null) {
                createUserAchievement(user, session, achievement);
            } else {
                log.log(Level.WARNING, "Achievement with id " + Constants.ACHIEVEMENT_KOMMENTARER_V1 + " was not found, " +
                        "and could not be added to user " + user.getUsername() + " with id " + user.getId());
            }
        }

        session.getTransaction().commit();
        session.close();
    }

    public void addRegisterAchievement(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Achievement achievement = getAchievement(Constants.ACHIEVEMENT_DELTAKER_V1);
        if (achievement != null) {
            createUserAchievement(user, session, achievement);
        } else {
            log.log(Level.WARNING, "Achievement with id " + Constants.ACHIEVEMENT_DELTAKER_V1 + " was not found, " +
                    "and could not be added to user " + user.getUsername() + " with id " + user.getId());
        }

        session.getTransaction().commit();
        session.close();
    }

    public void addAgreementAchievement(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Achievement achievement = getAchievement(Constants.ACHIEVEMENT_TOMMELOPP_V1);
        if (achievement != null) {
            UserAchievement existingAchievement = getUserAchievement(session, achievement, user);
            if (existingAchievement == null) {
                createUserAchievement(user, session, achievement);
            }
        } else {
            log.log(Level.WARNING, "Achievement with id " + Constants.ACHIEVEMENT_TOMMELOPP_V1 + " was not found, " +
                    "and could not be added to user " + user.getUsername() + " with id " + user.getId());
        }

        session.getTransaction().commit();
        session.close();
    }

    private void createUserAchievement(User user, Session session, Achievement avatarAchievement) {
        UserAchievement userAchievement = new UserAchievement();
        userAchievement.setUser(user);
        userAchievement.setAchievement(avatarAchievement);
        userAchievement.setDate(Calendar.getInstance().getTime());
        user.incrementScore(Constants.GET_ACHIEVEMENT_SCORE);
        session.save(userAchievement);
        session.update(user);
        logRecordDao.logAchievement(user, avatarAchievement);
    }
}
