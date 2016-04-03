package no.ntnu.mikaelr.service.dao;

import no.ntnu.mikaelr.model.dto.incoming.UserIncoming;
import no.ntnu.mikaelr.model.entities.User;
import no.ntnu.mikaelr.model.entities.UserRole;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(UserIncoming incomingUser) {
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

}
