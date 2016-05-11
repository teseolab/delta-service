package no.ntnu.mikaelr.util;

import no.ntnu.mikaelr.model.entities.*;
import no.ntnu.mikaelr.service.dao.ProjectDao;
import no.ntnu.mikaelr.util.TaskType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

public class TestData {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ProjectDao projectDao;

    private User user;
    private Project project;

    public void initializeTestData() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

//        Project bycampus = projectDao.getProject(2);
//        createTask(
//                session,
//                bycampus,
//                3,
//                TaskType.SCALE_TASK,
//                63.416178f,
//                10.402828f,
//                "Fortsett fremover til du når en vei, og følg deretter veien mot høyre. Du er fremme når du ser utover et stort grønt område.",
//                Arrays.asList(
//                        "Dette området kunne ha blitt mye bedre utnyttet.",
//                        "Jeg vil ha et lite skianlegg her.",
//                        "Dette er en god plass for å sette opp flere studentboliger."),
//                "http://129.241.102.204:8080/images/kjhasoiabhasjkhdf");
//        createTask(
//                session,
//                bycampus,
//                4,
//                TaskType.TEXT_TASK,
//                63.416799f,
//                10.405833f,
//                "Gå tilbake i Sem Sælands vei (der du kom fra). Neste punkt er langs denne veien.",
//                Collections.singletonList("På din venstre side ser du en liten åpen plass i hjertet av Gløshaugen. Har du noen forslag til hvordan denne plassen kunne blitt brukt på en best mulig måte?"),
//                "http://129.241.102.204:8080/images/skiboli");
//        createTask(
//                session,
//                bycampus,
//                4,
//                TaskType.TEXT_TASK,
//                63.416673f,
//                10.404051f,
//                "Fortsett fremover langs sentralbygget til du når en statue.",
//                Collections.singletonList("Du står nå like ved Hangaren, en av Sit sine kantiner på Gløshaugen. I hvilken kantine pleier du å spise og hvorfor?"));

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

    private void createTask(Session session, Project project, int order, TaskType taskType, float latitude, float longitude, String hint, List<String> descriptions, String imageUri) {
        Task task = new Task();
        task.setTaskOrder(order);
        task.setProject(project);
        task.setTaskType(taskType);
        task.setLatitude(latitude);
        task.setLongitude(longitude);
        task.setHint(hint);
        String[] descArray = new String[descriptions.size()];
        descArray = descriptions.toArray(descArray);
        task.setDescriptions(descArray);
        task.setImageUri(imageUri);
        session.save(task);
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
