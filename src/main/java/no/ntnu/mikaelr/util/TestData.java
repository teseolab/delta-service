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

    private Session session;

    public void initializeTestData() {

        session = sessionFactory.openSession();
        session.beginTransaction();

        Project nyhavna = projectDao.getProject(1);

        createTask(
                nyhavna,
                0,
                TaskType.ALTERNATIVE_TASK_MULTI,
                63.43712f,
                10.41736f,
                "Dra til det markerte punktet på kartet for starte oppdraget.",
                "Du fant frem! Du står nå ved Lademoen togstasjon og er forhåpentligvis klar for en reise gjennom Nyhavna. Etter å ha svart på denne oppgaven vil du få en beskrivelse hvordan du kommer deg til neste oppgave.",
                Collections.singletonList(
                        "Det første vi lurer på er: hvis du skulle bo på Nyhavna, på hvilken måte hadde du foretrukket å gjøre dine daglige reiser? Velg de alternativene som passer deg."),
                Collections.singletonList(
                        Arrays.asList("Til fots", "Med sykkel", "Med buss", "Med scooter/moped", "Med bil", "Med båt", "Med tog")),
                "http://129.241.113.73:8080/images/togstasjon.jpg");

        createTask(
                nyhavna,
                1,
                TaskType.ALTERNATIVE_TASK_MULTI,
                63.43883f,
                10.41813f,
                "Følg gangfeltet under jernbanen, kryss veien og ta til høyre langs Kjeldsberg Kaffebrenneri. Følg gangfeltet til du står mellom to rundkjøringer.",
                "Mye av industrien på Nyhavna skal omlokaliseres for å gjøre plass til blant annet boliger.",
                Arrays.asList(
                        "Hvis jeg skal vurdere å bo på Nyhavna, er det for meg viktig med…",
                        "Hvis jeg skal vurdere å bo på Nyhavna, er det for meg viktig med at det finnes boliger som er…",
                        "Hvis jeg skal vurdere å bo på Nyhavna, er det for meg viktig med nærhet til …"),
                Arrays.asList(
                        Arrays.asList("God tilgjengengelighet med bil", "Store fellesområder", "Offentlige parker", "Sykkelparkering", "Utsikt til vannet", "Gode solforhold", "Å slippe å ha bil"),
                        Arrays.asList("Billige", "Familievennlige", "Store", "Små", "Luksuriøse", "Med utleiemuligheter"),
                        Arrays.asList("Butikker", "Restauranter", "Havna", "Industrivirksomheter", "Kunstnermiljø", "Universitet", "Barnehage", "Barneskole", "Midtbyen", "Ladestien")),
                "http://129.241.113.73:8080/images/fyringsbunkeren.jpg");

        List<String> scale = Arrays.asList("Helt uenig", "Uenig", "Nøytral", "Enig", "Helt enig");

        createTask(
                nyhavna,
                2,
                TaskType.SCALE_TASK,
                63.44009f,
                10.416f,
                "Fortsett nordover gjennom rundkjøringen og følg Skippergata. Neste stopp er ved en stor bunker på høyre side av denne gata.",
                "Visste du at dette er en ubåt-bunker som ble bygget under 2. verdenskrig? Bunkeren kalles Dora I og utgjør et av flere byggverk på Nyhavna som regnes å ha stor historisk verdi. Mer info om denne bunkeren og Dora II kan du finne på dora.no. Hvordan stiller du deg til disse tre spørsmålene?",
                Arrays.asList(
                        "Jeg er godt kjent med de historiske byggverkene på Nyhavna.",
                        "Det er viktig at bunkeren beholder sin ytre karakter.",
                        "Området rundt bunkeren burde gjøres åpent for allmennheten."),
                Arrays.asList(scale, scale, scale),
                "http://129.241.113.73:8080/images/dora.jpg");

        createTask(
                nyhavna,
                3,
                TaskType.ALTERNATIVE_TASK_MULTI,
                63.44209f,
                10.42082f,
                "Gå tilbake til rundkjøringen med et stor anker i midten. Ta til venstre i rundkjøringen og følg  Kobbes gate ut på kaia.",
                "Nylig ble et prosjekt for å rense sjøbunnen i havneområdet ferdigstilt. Dette innebar å grave opp forurenset sjøbunn og plassere det i såkalte deponier. Hvordan dette fungerer kan du lese mer om på renerehavn.no.",
                Collections.singletonList(
                        "Området ytterst på kaia skal etter planen gjøres om til en offentlig park. Hva kunne du tenke deg å bruke denne parken til? Du kan velge opp til flere alternativer."),
                Collections.singletonList(
                        Arrays.asList("Dyrke grønnsaker", "Skate", "Leke", "Grille", "Sitte på gresset", "Slikke sol", "Treffe mennesker", "Spille ball", "Kaste frisbee")),
                "http://129.241.113.73:8080/images/kullkranpiren.jpg");

        scale = Arrays.asList("Helt uenig", "Uenig", "Vet ikke", "Enig", "Helt enig");

        createTask(
                nyhavna,
                4,
                TaskType.SCALE_TASK,
                63.44005f,
                10.42306f,
                "Gå litt tilbake og til venstre etter det første røde bygget. Ta til høyre når du kommer ut til kaia. Du kan nå følge kaia langs Dora II til du når Maskingata.",
                "Rett sør for der du står, over veien, ligger Svartlamoen. Dette er et byøkologisk område, som legger vekt på bærekraftige miljøløsninger, rimelig utleie, fellesskap og dugnad. Beboerne driver flere bedrifter på området, bla. Gratisbutikken, økologisk dagligvarehandel og kunstgalleri.\n\nHvordan stiller du deg til disse utsagnene?",
                Arrays.asList(
                        "Jeg kjenner godt til Svartlamoen.",
                        "Jeg har brukt noen av tilbudene på Svartlamoen.",
                        "Byøkologiske områder virker spennende.",
                        "Det er viktig å tenke bærekraftig i byer."),
                Arrays.asList(scale, scale, scale, scale),
                "http://129.241.113.73:8080/images/svartlamoen.jpg");

        createTask(
                nyhavna,
                5,
                TaskType.TEXT_TASK,
                63.44227f,
                10.42525f,
                "Fortsett videre langs kaia.",
                "I planbeskrivelsen for Nyhavna nevnes det som viktig å holde kaikanter og sjøfronter tilgjengelige for allmennheten. I dette havneområdet er det foreslått å legge til rette for bading! Et annet forslag er å lage en bro fra der du står over til Kullkranpirnen.",
                Collections.singletonList("Hva skal til for at du syntes et havneområde er en attraktiv plass å være?"),
                null,
                "http://129.241.113.73:8080/images/strandveikaia.jpg");

//        Project testProject = new Project();
//        testProject.setLatitude(10.304736f);
//        testProject.setLongitude(63.3481f);
//        testProject.setDescription("Testprosjekt");
//        testProject.setName("Testprosjekt");
//        session.save(testProject);

//        createTask(
//                testProject,
//                0,
//                TaskType.ALTERNATIVE_TASK,
//                63.4164f,
//                10.4027f,
//                "Gå i riktig retning",
//                "Velg et alternativ for hvert spørsmål.",
//                Arrays.asList(
//                        "Hvis jeg var en fugl hadde jeg...",
//                        "Hvis jeg var en hund hadde jeg..."),
//                Arrays.asList(
//                        Arrays.asList("Flydd høyt", "Badet i en damm", "Plaget katter"),
//                        Arrays.asList("Bjeffet", "Jaget min egen hale", "Blitt plaget av katter")),
//                null);
//
//        createTask(
//                testProject,
//                1,
//                TaskType.ALTERNATIVE_TASK_MULTI,
//                63.418f,
//                10.404f,
//                "Fortsett i riktig retning",
//                "Velg et eller flere alternativ for hvert spørsmål.",
//                Arrays.asList(
//                        "Hvis jeg var en fugl hadde jeg...",
//                        "Hvis jeg var en hund hadde jeg..."),
//                Arrays.asList(
//                        Arrays.asList("Flydd høyt", "Badet i en damm", "Plaget katter"),
//                        Arrays.asList("Bjeffet", "Jaget min egen hale", "Blitt plaget av katter")),
//                null);
//
//        List<String> scale = Arrays.asList("Helt uenig", "Uenig", "Vet ikke", "Enig", "Helt enig");
//
//        createTask(
//                testProject,
//                2,
//                TaskType.SCALE_TASK,
//                63.416178f,
//                10.402828f,
//                "Fortsett fremover til du når en vei, og følg deretter veien mot høyre. Du er fremme når du ser utover et stort grønt område.",
//                "Under er noen utsagn og denne plassen. Velg hvordan du stiller deg til hvert enkelt utsagn.",
//                Arrays.asList(
//                        "Dette området kunne ha blitt mye bedre utnyttet.",
//                        "Jeg vil ha et lite skianlegg her.",
//                        "Dette er en god plass for å sette opp flere studentboliger."),
//                Arrays.asList(scale, scale, scale),
//                null);
//
//        createTask(
//                testProject,
//                3,
//                TaskType.TEXT_TASK,
//                63.41615f,
//                10.4027f,
//                "Gå til du blir sliten.",
//                "Dette er beskrivelse av oppgave.",
//                Collections.singletonList("Hva syntes du om dette forslaget?"),
//                null,
//                null);

//        Achievement deltaker = createAchievement(Constants.ACHIEVEMENT_DELTAKER_V1, "Deltaker", "Regsitrere en bruker i Delta!", "ic_ach_deltaker_v1");
//        createUserAchievement(mikael, deltaker);
//        Achievement profilbilde = createAchievement(Constants.ACHIEVEMENT_PROFILBILDE, "Hey good lookin!", "Laste opp et profilbilde", "ic_ach_profilbilde");
//        createUserAchievement(mikael, profilbilde);
//        Achievement forslag = createAchievement(Constants.ACHIEVEMENT_DELTAKER_V2, "Deltaker v2", "Poste et forslag for første gang", "ic_ach_deltaker_v2");
//        createUserAchievement(mikael, forslag);
//        Achievement kommentar = createAchievement(Constants.ACHIEVEMENT_KOMMENTARER_V1, "Icebreaker", "Poste fem kommentarer", "ic_ach_kommentarer_v1");
//        createUserAchievement(mikael, kommentar);
//        Achievement tommelopp = createAchievement(Constants.ACHIEVEMENT_TOMMELOPP_V1, "Tommel opp", "Motta fem tommel opp på et forslag", "ic_ach_tommelopp");
//        createUserAchievement(mikael, tommelopp);

//        Project nyhavna = createNyhavnaProject();
//        createNyhavnaTasks(nyhavna);
//        createSuggestion(
//                nyhavna,
//                mikael,
//                "Vedlikehold av gamle bygninger",
//                "Jeg syntes flere av bygningene på Nyhavna har en viss sjarm over seg, og som burde bevares. Det er derfor trist å se at flere bygninger er i dårlig stand. Det burde brukes mer ressurser på å holde disse bygningene vedlike.",
//                Calendar.getInstance().getTime());

//        Project bycampus = createBycampusProject();
//        createBycampusTasks(bycampus);

//        Project torvet = createTorvetProject();
//        createTorvetTasks(torvet);

        session.getTransaction().commit();
        session.close();

    }

    private Project createBycampusProject() {
        return createProject(
                    "Gløshaugen",
                    "Gløshaugen campus skal utvides",
                    63.419676f,
                    10.401622f,
                    "129.241.102.204:8080/images/d8ef4dd5689a.jpg");
    }

//    private void createBycampusTasks(Project bycampus) {
//        createTask(
//                bycampus,
//                0,
//                TaskType.SCALE_TASK,
//                63.419582f,
//                10.400352f,
//                "Gå ut på gresset til høyre.",
//                "Under er noen utsagn og denne plassen. Velg hvordan du stiller deg til hvert enkelt utsagn.",
//                Arrays.asList(
//                        "Utsikten må bevares.",
//                        "Dette burde være et friluftsområde åpent for alle.",
//                        "Det burde være flere benker her."
//                ),
//                null);
//
//        createTask(
//                bycampus,
//                1,
//                TaskType.TEXT_TASK,
//                63.418786f,
//                10.402683f,
//                "Bak hovedbygget er en firkantet plen. Gå dit.",
//                "Det er foreslått å bygge noe her. Hva tenker du om det?",
//                null,
//                null);
//
//        createTask(
//                bycampus,
//                2,
//                TaskType.TEXT_TASK,
//                63.416673f,
//                10.404051f,
//                "Fortsett fremover langs sentralbygget til du når en statue.",
//                "Du står nå like ved Hangaren, en av Sit sine kantiner på Gløshaugen. I hvilken kantine pleier du å spise og hvorfor?",
//                null,
//                null);
//
//        createTask(
//                bycampus,
//                3,
//                TaskType.SCALE_TASK,
//                63.416178f,
//                10.402828f,
//                "Fortsett fremover til du når en vei, og følg deretter veien mot høyre. Du er fremme når du ser utover et stort grønt område.",
//                "Under er noen utsagn og denne plassen. Velg hvordan du stiller deg til hvert enkelt utsagn.",
//                Arrays.asList(
//                        "Dette området kunne ha blitt mye bedre utnyttet.",
//                        "Jeg vil ha et lite skianlegg her.",
//                        "Dette er en god plass for å sette opp flere studentboliger."),
//                null);
//
//        createTask(
//                bycampus,
//                4,
//                TaskType.TEXT_TASK,
//                63.416799f,
//                10.405833f,
//                "Gå tilbake i Sem Sælands vei (der du kom fra). Neste punkt er langs denne veien.",
//                "På din venstre side ser du en liten åpen plass i hjertet av Gløshaugen. Har du noen forslag til hvordan denne plassen kunne blitt brukt på en best mulig måte?",
//                null,
//                null);
//    }

    private Project createNyhavnaProject() {
        return createProject(
                    "Nyhavna",
                    "På Nyhavna er det et betydelig utbyggingspotensial som det er naturlig å vurdere opp mot vedtatte byutviklingsstrategier. Samtidig kan det være viktig å ivareta Nyhavnas rolle som havn med plass til virksomheter som naturlig hører hjemme i havneområdet.",
                    63.439207f,
                    10.419620f,
                    "https://www.trondheim.kommune.no/multimedia/1115028694/Fotoill.-3_stort-oversiktsbilde.jpg");
    }
//    private void createNyhavnaTasks(Project nyhavna) {
//        createTask(
//                nyhavna,
//                0,
//                TaskType.SCALE_TASK,
//                63.439724f,
//                10.415015f,
//                "Følg Styrmannsgata ut mot bryggen.",
//                "Under er noen utsagn og denne plassen. Velg hvordan du stiller deg til hvert enkelt utsagn.",
//                Arrays.asList(
//                        "Dette er en fin plass.",
//                        "Dette burde være et friluftsområde åpent for alle.",
//                        "Området burde være forebeholdt industri."
//                ),
//                "http://www.koteng.no/multimedia/570/DJI00021.jpg");
//
//        createTask(
//                nyhavna,
//                1,
//                TaskType.TEXT_TASK,
//                63.440595f,
//                10.413505f,
//                "Gå gjennom det nærmeste veikrysset og ut mot kaia.",
//                "Dette bildet vister mange bygg. Beskriv kort hva du tenker om dette forslaget.",
//                null,
//                "http://trondheimhavn.no/uploads/bilder/nyheter/2011/10/111026+Kanalbo.jpg");
//
//        createTask(
//                nyhavna,
//                2,
//                TaskType.ALTERNATIVE_TASK,
//                63.442737f,
//                10.415833f,
//                "Gå lenger ut",
//                "Hva vil du ha her?",
//                Arrays.asList("Bensinstasjon", "Isbar", "Drive-in kino", "Rema 1000", "Fotballbane", "Badebasseng", "Annet"),
//                "http://2.bp.blogspot.com/-Jfll5rbk7YI/UHsZ_Vt6pfI/AAAAAAAAJ_E/hvZ6WwDt778/s1600/IMG_5161-Sollys-ved-Nyhavna_1.jpg");
//    }
//
//    private Project createTorvetProject() {
//        return createProject(
//                "Trondheim Torg",
//                "Trondheim Torg skal fornyes. Det skal blant annet komme mange flere spiseplasser i området.",
//                63.430493f,
//                10.395041f,
//                "129.241.102.204:8080/images/dsc016451.jpg"
//        );
//    }

    private void createTorvetTasks(Project torvet) {
//        createTask(
//                torvet,
//                0,
//                TaskType.SCALE_TASK,
//                63.430253f,
//                10.397717f,
//                "Gå østover i Kongens gate mot Vår Frue kirke.",
//                "Under er noen utsagn og denne plassen. Velg hvordan du stiller deg til hvert enkelt utsagn.",
//                Arrays.asList(
//                        "Det burde være restauranter langs denne strekningen.",
//                        "Hele strekningen burde bli omgjort til gågate",
//                        "Det burde være flere benker her."),
//                null);
//
//        createTask(
//                torvet,
//                1,
//                TaskType.TEXT_TASK,
//                63.430810f,
//                10.395941f,
//                "Gå tilbake til torget og opp i øvre nordre hjørne av torgsplassen.",
//                "Se utover torget og forestill deg et yrende folkeliv. Hva ønsker du at man skal kunne gjøre på torget?",
//                null,
//                null);
    }
    private Project createProject(String name, String description, float latitude, float longitude, String imageUri) {
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setLatitude(latitude);
        project.setLongitude(longitude);
        project.setImageUri(imageUri);
        session.save(project);
        return project;
    }

    private void createSuggestion(Project project, User user, String title, String details, Date date) {
        Suggestion suggestion = new Suggestion();
        suggestion.setTitle(title);
        suggestion.setDetails(details);
        suggestion.setDate(date);
        suggestion.setUser(user);
        suggestion.setProject(project);
        session.save(suggestion);
    }
    private void createTask(Project project, int order, TaskType taskType, float latitude, float longitude, String hint, String description, List<String> questions, List<List<String>> alternatives, String imageUri) {
        Task task = new Task();
        task.setTaskOrder(order);
        task.setImageUri(imageUri);
        task.setProject(project);
        task.setTaskType(taskType);
        task.setLatitude(latitude);
        task.setLongitude(longitude);
        task.setHint(hint);
        task.setDescription(description);
        session.save(task);

        for (int i = 0; i < questions.size(); i++) {
            TaskQuestion taskQuestion = new TaskQuestion();
            taskQuestion.setQuestion(questions.get(i));
            if (alternatives != null) {
                taskQuestion.setAlternatives(alternatives.get(i));
            }
            taskQuestion.setTask(task);
            session.save(taskQuestion);
        }
    }
    private User createAdminUser(String username, String password, String avatarUri) {
        User newUser = new User(username, passwordEncoder.encode(password));
        newUser.setAvatarUri(avatarUri);
        session.save(newUser);
        UserRole userRole = new UserRole();
        userRole.setRole("USER");
        userRole.setUser(newUser);
        session.save(userRole);
        UserRole adminRole = new UserRole();
        adminRole.setRole("ADMIN");
        adminRole.setUser(newUser);
        session.save(adminRole);
        return newUser;
    }
    private User createUser(String username, String password, String avatarUri) {
        User newUser = new User(username, passwordEncoder.encode(password));
        newUser.setAvatarUri(avatarUri);
        session.save(newUser);
        UserRole userRole = new UserRole();
        userRole.setRole("USER");
        userRole.setUser(newUser);
        session.save(userRole);
        return newUser;
    }
    private Achievement createAchievement(int id, String name, String description, String badgeName) {
        Achievement achievement = new Achievement();
        achievement.setAcgievementId(id);
        achievement.setName(name);
        achievement.setDescription(description);
        achievement.setBadgeName(badgeName);
        session.save(achievement);
        return achievement;
    }
    private UserAchievement createUserAchievement(User user, Achievement achievement) {
        UserAchievement userAchievement = new UserAchievement();
        userAchievement.setUser(user);
        userAchievement.setAchievement(achievement);
        userAchievement.setDate(Calendar.getInstance().getTime());
        session.save(userAchievement);
        return userAchievement;
    }

    public void createAdmin() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        User mikael = createAdminUser("mikael", "123", "http://129.241.113.73:8080/images/kedhpyqycqvqdzllhzyj.jpg");
        session.getTransaction().commit();
        session.close();
    }
}
