package no.ntnu.mikaelr.util;

import no.ntnu.mikaelr.model.entities.*;
import no.ntnu.mikaelr.service.dao.ProjectDao;
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
        project = createProject(session);
//        createProject2(session);
//        createProject3(session);
//
//        createSuggestionWithComment(session);

        session.getTransaction().commit();
        session.close();

    }

    private void createTask(Session session, Project project, int order, TaskType taskType, float latitude, float longitude, String hint, String description, List<String> tasks, String imageUri) {
        Task task = new Task();
        task.setTaskOrder(order);
        task.setProject(project);
        task.setTaskType(taskType);
        task.setLatitude(latitude);
        task.setLongitude(longitude);
        task.setHint(hint);
        task.setDescription(description);
        task.setTaskElements(tasks);
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
        project.setImageUri("https://www.trondheim.kommune.no/multimedia/1115028694/Fotoill.-3_stort-oversiktsbilde.jpg");
        session.save(project);
        createProjectTasks(session, project);
        return project;
    }

    private void createProjectTasks(Session session, Project project) {

        Task newTask = new Task();
        newTask.setProject(project);
        newTask.setTaskOrder(1);
        newTask.setTaskType(TaskType.SCALE_TASK);
        newTask.setLatitude(63.439724f);
        newTask.setLongitude(10.415015f);
        newTask.setImageUri("http://www.koteng.no/multimedia/570/DJI00021.jpg");
        newTask.setHint("Følg Styrmannsgata ut mot bryggen.");
        String description = "Under er noen utsagn og denne plassen. Velg hvordan du stiller deg til hvert enkelt utsagn.";
        newTask.setDescription(description);
        String task1 = "Dette er en fin plass.";
        String task2 = "Dette burde være et friluftsområde åpent for alle.";
        String task3 = "Området burde være forebeholdt industri.";
        List<String> taskElements = new ArrayList<String>();
        taskElements.add(task1);
        taskElements.add(task2);
        taskElements.add(task3);
        newTask.setTaskElements(taskElements);
        session.save(newTask);

        Task newTask2 = new Task();
        newTask2.setTaskOrder(2);
        newTask2.setProject(project);
        newTask2.setTaskType(TaskType.TEXT_TASK);
        newTask2.setLatitude(63.440595f);
        newTask2.setLongitude(10.413505f);
        newTask.setImageUri("http://trondheimhavn.no/uploads/bilder/nyheter/2011/10/111026+Kanalbo.jpg");
        newTask2.setHint("Gå gjennom det nærmeste veikrysset og ut mot kaia.");
        newTask2.setDescription("Dette bildet vister mange bygg. Beskriv kort hva du tenker om dette forslaget.");
        session.save(newTask2);

        Task newTask3 = new Task();
        newTask3.setTaskOrder(0);
        newTask3.setProject(project);
        newTask3.setTaskType(TaskType.ALTERNATIVE_TASK);
        newTask3.setLatitude(63.442737f);
        newTask3.setLongitude(10.415833f);
        newTask3.setImageUri("http://2.bp.blogspot.com/-Jfll5rbk7YI/UHsZ_Vt6pfI/AAAAAAAAJ_E/hvZ6WwDt778/s1600/IMG_5161-Sollys-ved-Nyhavna_1.jpg");
        newTask3.setHint("Gå lenger ut");
        newTask3.setDescription("Hva vil du ha her?");
        String alternative1 = "Bensinstasjon";
        String alternative2 = "Isbar";
        String alternative3 = "Drive-in kino";
        String alternative4 = "Rema 1000";
        String alternative5 = "Fotballbane";
        String alternative6 = "Badebasseng";
        String alternative7 = "Annet";
        taskElements = new ArrayList<String>();
        taskElements.add(alternative1);
        taskElements.add(alternative3);
        taskElements.add(alternative2);
        taskElements.add(alternative4);
        taskElements.add(alternative5);
        taskElements.add(alternative6);
        taskElements.add(alternative7);
        newTask3.setTaskElements(taskElements);
        session.save(newTask3);

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

        Task newTask = new Task();
        newTask.setProject(project);
        newTask.setTaskType(TaskType.SCALE_TASK);
        newTask.setLatitude(63.419582f);
        newTask.setLongitude(10.400352f);
        newTask.setHint("Gå ut på gresset til høyre.");
        newTask.setDescription("Under er noen utsagn og denne plassen. Velg hvordan du stiller deg til hvert enkelt utsagn.");
        String task1 = "Utsikten må bevares.";
        String task2 = "Dette burde være et friluftsområde åpent for alle.";
        String task3 = "Det burde være flere benker her.";
        List<String> taskElements = new ArrayList<String>();
        taskElements.add(task1);
        taskElements.add(task2);
        taskElements.add(task3);
        newTask.setTaskElements(taskElements);
        session.save(task1);

        Task newTask2 = new Task();
        newTask2.setProject(project);
        newTask2.setTaskType(TaskType.TEXT_TASK);
        newTask2.setLatitude(63.418786f);
        newTask2.setLongitude(10.402683f);
        newTask2.setHint("Bak hovedbygget er en firkantet plen. Gå dit.");
        newTask2.setDescription("Det er foreslått å bygge noe her. Hva tenker du om det?");
        session.save(newTask2);

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

        Task newTask = new Task();
        newTask.setProject(project);
        newTask.setTaskType(TaskType.SCALE_TASK);
        newTask.setLatitude(63.430253f);
        newTask.setLongitude(10.397717f);
        newTask.setHint("Gå østover i Kongens gate mot Vår Frue kirke.");
        newTask.setDescription("Under er noen utsagn og denne plassen. Velg hvordan du stiller deg til hvert enkelt utsagn.");
        String task1 = "Det burde være restauranter langs denne strekningen.";
        String task2 = "Hele strekningen burde bli omgjort til gågate";
        String task3 = "Det burde være flere benker her.";
        List<String> taskElements = new ArrayList<String>();
        taskElements.add(task1);
        taskElements.add(task2);
        taskElements.add(task3);
        newTask.setTaskElements(taskElements);
        session.save(task1);

        Task newTask2 = new Task();
        newTask2.setProject(project);
        newTask2.setTaskType(TaskType.TEXT_TASK);
        newTask2.setLatitude(63.430810f);
        newTask2.setLongitude(10.395941f);
        newTask2.setHint("Gå tilbake til torget og opp i øvre nordre hjørne av torgsplassen.");
        newTask2.setDescription("Se utover torget og forestill deg et yrende folkeliv. Hva ønsker du at man skal kunne gjøre på torget?");
        session.save(newTask2);

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
