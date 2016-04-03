package no.ntnu.mikaelr;

import no.ntnu.mikaelr.model.entities.Project;
import no.ntnu.mikaelr.model.entities.Task;
import no.ntnu.mikaelr.model.entities.User;
import no.ntnu.mikaelr.model.entities.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestData {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void initializeTestData() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        createUser(session);
        createProject(session);
        createProject2(session);

        session.getTransaction().commit();
        session.close();

    }

    private void createProject(Session session) {
        Project project = new Project();
        project.setName("Nyhavna");
        project.setDescription("På Nyhavna er det et betydelig utbyggingspotensial som det er naturlig å vurdere opp mot vedtatte byutviklingsstrategier. Samtidig kan det være viktig å ivareta Nyhavnas rolle som havn med plass til virksomheter som naturlig hører hjemme i havneområdet. Hensikten med en ny kommunedelplan er å få overordnede langsiktige strategier og rammer for utvikling av Nyhavna.");
        project.setLatitude(63.439207f);
        project.setLongitude(10.419620f);
        session.save(project);

        createProjectTasks(session, project);
    }

    private void createProjectTasks(Session session, Project project) {

        Task task1 = new Task();
        task1.setProject(project);
        task1.setTaskType(TaskType.TEXT_TASK);
        task1.setLatitude(63.439724f);
        task1.setLongitude(10.415015f);
        task1.setHint("Følg Styrmannsgata ut mot bryggen.");
        String[] taskDescriptions = {"Dette er en fin plass", "Dette burde være et friluftsområde åpent for alle", "Området burde være forebeholdt industri."};
        task1.setDescriptions(taskDescriptions);
        session.save(task1);

        Task task2 = new Task();
        task2.setProject(project);
        task2.setTaskType(TaskType.SCALE_TASK);
        task2.setLatitude(63.440595f);
        task2.setLongitude(10.413505f);
        task2.setHint("Gå gjennom det nærmeste veikrysset og ut mot kaia.");
        String[] taskDescriptions2 = {"Dette bildet vister mange bygg. Beskriv kort hva du tenker om dette forslaget."};
        task2.setDescriptions(taskDescriptions2);
        session.save(task2);

    }private void createProject2(Session session) {
        Project project = new Project();
        project.setName("Gløshaugen");
        project.setDescription("Gløshaugen campus skal utvides");
        project.setLatitude(63.419786f);
        project.setLongitude(10.401809f);
        session.save(project);

        createProjectTasks2(session, project);
    }

    private void createProjectTasks2(Session session, Project project) {

        Task task1 = new Task();
        task1.setProject(project);
        task1.setTaskType(TaskType.TEXT_TASK);
        task1.setLatitude(63.419629f);
        task1.setLongitude(10.400275f);
        task1.setHint("På ut på gresset til høyre.");
        String[] taskDescriptions = {"Utsikten må bevares", "Dette burde være et friluftsområde åpent for alle", "Det burde være flere benker her."};
        task1.setDescriptions(taskDescriptions);
        session.save(task1);

        Task task2 = new Task();
        task2.setProject(project);
        task2.setTaskType(TaskType.SCALE_TASK);
        task2.setLatitude(63.418592f);
        task2.setLongitude(10.402944f);
        task2.setHint("Bak hovedbygget er en firkantet plen. Gå dit.");
        String[] taskDescriptions2 = {"Det er foreslått å bygge noe her. Hva tenker du om det?."};
        task2.setDescriptions(taskDescriptions2);
        session.save(task2);

    }

    private void createUser(Session session) {
        User newUser = new User("Mikael", passwordEncoder.encode("123"));
        session.save(newUser);

        UserRole userRole = new UserRole();
        userRole.setRole("USER");
        userRole.setUser(newUser);
        session.save(userRole);
    }

}
