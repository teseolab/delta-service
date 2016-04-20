package no.ntnu.mikaelr;

import no.ntnu.mikaelr.model.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;

public class TestData {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    PasswordEncoder passwordEncoder;

    private User user;
    private Project project;

    public void initializeTestData() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

//        createUser(session, "Steffen", "123");

//        user = createUser(session);
//
//        project = createProject(session);
//        createProject2(session);
//        createProject3(session);
//
//        createSuggestionWithComment(session);

        session.getTransaction().commit();
        session.close();

    }

    private void createSuggestionWithComment(Session session) {
        Suggestion suggestion = new Suggestion();
        suggestion.setTitle("Vedlikehold av gamle bygninger");
        suggestion.setDetails("Jeg syntes flere av bygningene på Nyhavna har en viss sjarm over seg, og som burde bevares. Det er derfor trist å se at flere bygninger er i dårlig stand. Det burde brukes mer ressurser på å holde disse bygningene vedlike.");
        suggestion.setDate(Calendar.getInstance().getTime());
        suggestion.setUser(user);
        suggestion.setProject(project);
        session.save(suggestion);

        Comment comment = new Comment();
        comment.setDate(Calendar.getInstance().getTime());
        comment.setUser(user);
        comment.setComment("Jeg syntes også at flere av bygningene har en karakter som er verdt å ta vare på :)");
        comment.setSuggestion(suggestion);
        session.save(comment);

        Comment comment2 = new Comment();
        comment2.setDate(Calendar.getInstance().getTime());
        comment2.setUser(user);
        comment2.setComment("Jeg er ikke enig i at flere av bygningene har en karakter som er verdt å ta vare på :( Jeg mener derimot at man burde rive alt sammen og bygge superkule og moderne bygg!");
        comment2.setSuggestion(suggestion);
        session.save(comment2);

        Comment comment3 = new Comment();
        comment3.setDate(Calendar.getInstance().getTime());
        comment3.setUser(user);
        comment3.setComment("Jeg syntes også at flere av bygningene har en karakter som er verdt å ta vare på :)");
        comment3.setSuggestion(suggestion);
        session.save(comment3);

        Comment comment4 = new Comment();
        comment4.setDate(Calendar.getInstance().getTime());
        comment4.setUser(user);
        comment4.setComment("Jeg er ikke enig i at flere av bygningene har en karakter som er verdt å ta vare på :( Jeg mener derimot at man burde rive alt sammen og bygge superkule og moderne bygg!");
        comment4.setSuggestion(suggestion);
        session.save(comment4);
    }

    private Project createProject(Session session) {
        Project project = new Project();
        project.setName("Nyhavna");
        project.setDescription("På Nyhavna er det et betydelig utbyggingspotensial som det er naturlig å vurdere opp mot vedtatte byutviklingsstrategier. Samtidig kan det være viktig å ivareta Nyhavnas rolle som havn med plass til virksomheter som naturlig hører hjemme i havneområdet.");
        project.setLatitude(63.439207f);
        project.setLongitude(10.419620f);
        session.save(project);
        createProjectTasks(session, project);
        return project;
    }

    private void createProjectTasks(Session session, Project project) {

        Task task1 = new Task();
        task1.setProject(project);
        task1.setTaskType(TaskType.SCALE_TASK);
        task1.setLatitude(63.439724f);
        task1.setLongitude(10.415015f);
        task1.setHint("Følg Styrmannsgata ut mot bryggen.");
        String[] taskDescriptions = {"Dette er en fin plass.", "Dette burde være et friluftsområde åpent for alle.", "Området burde være forebeholdt industri."};
        task1.setDescriptions(taskDescriptions);
        session.save(task1);

        Task task2 = new Task();
        task2.setProject(project);
        task2.setTaskType(TaskType.TEXT_TASK);
        task2.setLatitude(63.440595f);
        task2.setLongitude(10.413505f);
        task2.setHint("Gå gjennom det nærmeste veikrysset og ut mot kaia.");
        String[] taskDescriptions2 = {"Dette bildet vister mange bygg. Beskriv kort hva du tenker om dette forslaget."};
        task2.setDescriptions(taskDescriptions2);
        session.save(task2);

    }

    private void createProject2(Session session) {
        Project project = new Project();
        project.setName("Gløshaugen");
        project.setDescription("Gløshaugen campus skal utvides");
        project.setLatitude(63.419676f);
        project.setLongitude(10.401622f);
        session.save(project);

        createProjectTasks2(session, project);
    }

    private void createProjectTasks2(Session session, Project project) {

        Task task1 = new Task();
        task1.setProject(project);
        task1.setTaskType(TaskType.SCALE_TASK);
        task1.setLatitude(63.419582f);
        task1.setLongitude(10.400352f);
        task1.setHint("Gå ut på gresset til høyre.");
        String[] taskDescriptions = {"Utsikten må bevares.", "Dette burde være et friluftsområde åpent for alle.", "Det burde være flere benker her."};
        task1.setDescriptions(taskDescriptions);
        session.save(task1);

        Task task2 = new Task();
        task2.setProject(project);
        task2.setTaskType(TaskType.TEXT_TASK);
        task2.setLatitude(63.418786f);
        task2.setLongitude(10.402683f);
        task2.setHint("Bak hovedbygget er en firkantet plen. Gå dit.");
        String[] taskDescriptions2 = {"Det er foreslått å bygge noe her. Hva tenker du om det?"};
        task2.setDescriptions(taskDescriptions2);
        session.save(task2);

    }

    private void createProject3(Session session) {
        Project project = new Project();
        project.setName("Trondheim Torg");
        project.setDescription("Trondheim Torg skal fornyes. Det skal blant annet komme mange flere spiseplasser i området.");
        project.setLatitude(63.430493f);
        project.setLongitude(10.395041f);
        session.save(project);

        createProjectTasks3(session, project);
    }

    private void createProjectTasks3(Session session, Project project) {

        Task task1 = new Task();
        task1.setProject(project);
        task1.setTaskType(TaskType.SCALE_TASK);
        task1.setLatitude(63.430253f);
        task1.setLongitude(10.397717f);
        task1.setHint("Gå østover i Kongens gate mot Vår Frue kirke.");
        String[] taskDescriptions = {"Det burde være restauranter langs denne strekningen.", "Hele strekningen burde bli omgjort til gågate", "Det burde være flere benker her."};
        task1.setDescriptions(taskDescriptions);
        session.save(task1);

        Task task2 = new Task();
        task2.setProject(project);
        task2.setTaskType(TaskType.TEXT_TASK);
        task2.setLatitude(63.430810f);
        task2.setLongitude(10.395941f);
        task2.setHint("Gå tilbake til torget og opp i øvre nordre hjørne av torgsplassen.");
        String[] taskDescriptions2 = {"Se utover torget og forestill deg et yrende folkeliv. Hva ønsker du at man skal kunne gjøre på torget?"};
        task2.setDescriptions(taskDescriptions2);
        session.save(task2);

    }

    private User createUser(Session session) {
        User newUser = new User("Mikael", passwordEncoder.encode("123"));
        session.save(newUser);

        UserRole userRole = new UserRole();
        userRole.setRole("USER");
        userRole.setUser(newUser);
        session.save(userRole);
        return newUser;
    }

    private User createUser(Session session, String username, String password) {
        User newUser = new User(username, passwordEncoder.encode(password));
        session.save(newUser);

        UserRole userRole = new UserRole();
        userRole.setRole("USER");
        userRole.setUser(newUser);
        session.save(userRole);
        return newUser;
    }

}
