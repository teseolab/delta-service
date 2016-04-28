package no.ntnu.mikaelr.beans;

import no.ntnu.mikaelr.util.TestData;
import no.ntnu.mikaelr.security.AuthenticationSuccessHandlerImpl;
import no.ntnu.mikaelr.service.dao.*;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public ProjectResponseDao projectResponseDao() {
        return new ProjectResponseDao();
    }

    @Bean
    public SuggestionDao suggestionDao() {
        return new SuggestionDao();
    }

    @Bean
    public TaskDao taskDao() {
        return new TaskDao();
    }

    @Bean
    public LogRecordDao logRecordDao() {
        return new LogRecordDao();
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
