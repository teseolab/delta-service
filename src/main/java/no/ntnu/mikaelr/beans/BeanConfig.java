package no.ntnu.mikaelr.beans;

import no.ntnu.mikaelr.TestData;
import no.ntnu.mikaelr.security.AuthenticationSuccessHandlerImpl;
import no.ntnu.mikaelr.service.dao.ProjectDao;
import no.ntnu.mikaelr.service.dao.UserDao;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
public class BeanConfig {

    @Bean
    public TestData testData() {
        return new TestData();
    }

    @Bean
    public UserDao userDao() {
        return new UserDao();
    }

    @Bean
    public ProjectDao projectDao() {
        return new ProjectDao();
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

//    @Bean
//    public HibernateTransactionManager txManager() {
//        return new HibernateTransactionManager(sessionFactory());
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler failureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new AuthenticationSuccessHandlerImpl();
    }

}
