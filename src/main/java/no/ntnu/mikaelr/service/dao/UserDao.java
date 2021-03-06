package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.dto.incoming.UserIn;
import no.ntnu.mikaelr.model.entities.Suggestion;
import no.ntnu.mikaelr.model.entities.User;
import no.ntnu.mikaelr.model.entities.UserAchievement;
import no.ntnu.mikaelr.model.entities.UserRole;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(UserIn incomingUser) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User newUser = new User(incomingUser.getUsername(), passwordEncoder.encode(incomingUser.getPassword()));
        session.save(newUser);

        UserRole userRole = new UserRole();
        userRole.setRole("USER");
        userRole.setUser(newUser);
        session.save(userRole);

        session.getTransaction().commit();
        session.close();

        return newUser;
    }

    public User getUserById(Integer userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, userId);

        if (user != null) {
            Hibernate.initialize(user);
        }

        session.getTransaction().commit();
        session.close();

        return user;
    }

    public User getUserByUsername(String username) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(User.class);
        User user = (User)criteria.add(Restrictions.eq("username", username)).uniqueResult();
        if (user != null) {
            Hibernate.initialize(user.getRoles());
        }

        session.getTransaction().commit();
        session.close();

        return user;
    }

    public List<User> getTopUsers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User u ORDER BY u.score DESC");
        query.setFirstResult(0);
        query.setMaxResults(20);
        @SuppressWarnings("unchecked")
        List<User> result = query.list();

        session.getTransaction().commit();
        session.close();
        return result;
    }

    public int getNumberOfMissionsCompleted(int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, userId);
        int numberOfMissionsCompleted = user.getFinishedMissions().size();

        session.getTransaction().commit();
        session.close();
        return numberOfMissionsCompleted;
    }

    public int getNumberOfSuggestionsPosted(int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, userId);
        int numberOfSuggestionsPosted = user.getSuggestions().size();

        session.getTransaction().commit();
        session.close();
        return numberOfSuggestionsPosted;
    }

    public int getNumberOfCommentsPosted(int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, userId);
        int numberOfCommentsPosted = user.getComments().size();

        session.getTransaction().commit();
        session.close();
        return numberOfCommentsPosted;
    }

    public void putAvatarUri(String avatarUri, int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, userId);
        user.setAvatarUri(avatarUri);

        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public List<UserAchievement> getUserAchievements(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from UserAchievement where user = :user order by date desc");
        query.setParameter("user", user);

        @SuppressWarnings("unchecked")
        List<UserAchievement> userAchievements = query.list();

        session.getTransaction().commit();
        session.close();
        return userAchievements;
    }

    public void deleteUser(Integer userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, userId);
        session.delete(user);

        session.getTransaction().commit();
        session.close();
    }
}
