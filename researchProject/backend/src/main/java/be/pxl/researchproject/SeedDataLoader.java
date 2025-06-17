package be.pxl.researchproject;

import be.pxl.researchproject.domain.*;
import be.pxl.researchproject.repository.FoalRepository;
import be.pxl.researchproject.repository.MareRepository;
import be.pxl.researchproject.repository.StallionRepository;
import be.pxl.researchproject.repository.UserRepository;
import be.pxl.researchproject.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeedDataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final MareRepository mareRepository;
    private final StallionRepository stallionRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private FoalRepository foalRepository;

    public SeedDataLoader(UserRepository userRepository, MareRepository mareRepository, StallionRepository stallionRepository) {
        this.userRepository = userRepository;
        this.mareRepository = mareRepository;
        this.stallionRepository = stallionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed data creation logic
        //createUsers();
        //createStallions();
        //createMaresAndAttributes();
        //createNotifications();
    }
    private void createNotifications() {
        notificationService.deleteAllNotifications();

        List<Mare> mares = mareRepository.findAll();
        List<Foal> foals = foalRepository.findAll();

        notificationService.generateNotifications(mares, foals);
    }

    private void createUsers(){
        User adminUser = new User("admin@test.com", "$2a$12$FuiDn2plI0wF25T.HRHw/.7F/T72md4mpeQBUUyi.vVtJE9.aenxW", Roles.ADMIN);
        User regularUser = new User("user@test.com", "$2a$12$2RO/Yq6yg4Yi2Wsb8pZZSOlTd.Iub0sDQdq66lrFtuycmphQYowPW", Roles.STABLEKEEPER);
        User marviUser = new User("marvihendriks@gmail.com", "$2a$12$2RO/Yq6yg4Yi2Wsb8pZZSOlTd.Iub0sDQdq66lrFtuycmphQYowPW", Roles.STABLEKEEPER);
        User bramUser = new User("verbeirenb@outlook.com", "$2a$12$2RO/Yq6yg4Yi2Wsb8pZZSOlTd.Iub0sDQdq66lrFtuycmphQYowPW", Roles.STABLEKEEPER);
        userRepository.saveAll(List.of(adminUser, regularUser, marviUser, bramUser));
    }

    private void createStallions(){
        Stallion bob = new Stallion("Bob", java.time.LocalDate.of(2010, 5, 10), 1.75, "Stallion" ,"ECB1");
        Stallion dave = new Stallion("Dave", java.time.LocalDate.of(2009, 9, 20), 1.80, "Stallion" ,"ECB2");
        Stallion jules = new Stallion("Jules", java.time.LocalDate.of(2011, 3, 8), 1.70, "Stallion" ,"EDA5");
        stallionRepository.saveAll(List.of(bob, dave, jules));
    }

    private void createMaresAndAttributes(){
        Mare daisy = new Mare("Daisy", java.time.LocalDate.of(2014, 12, 24), 1.50, "Mare");

        List<DiaryEntry> daisyDiary = new ArrayList<>();
        daisyDiary.add(new DiaryEntry("Veulen Sparrow geboren", LocalDateTime.of(2023, 3, 14, 10, 5)));
        daisyDiary.add(new DiaryEntry("Veulen Sparrow Doet het prima", LocalDateTime.of(2023, 5, 18, 10, 5)));
        daisyDiary.add(new DiaryEntry("Daisy vertoont raar gedrag tijdens zwangerschap", LocalDateTime.of(2022, 2, 4, 1, 20)));
        daisyDiary.add(new DiaryEntry("Daisy succesvol gedekt door Bob", LocalDateTime.of(2024, 2, 4, 1, 20)));
        daisyDiary.add(new DiaryEntry("Daisy Bevallen", LocalDateTime.of(2024, 10, 9, 1, 20)));
        daisy.setDiary(daisyDiary);

        Foal foal = new Foal("Sparrow", LocalDate.now().minusMonths(1), 0.8, "stallion", "Bob");
        Client client = new Client("Hanne Geusens", "hannegeusens@hotmail.com", "0498653325", "Boesestraat 12, 3670 Oudsbergen", "Boesestraat 12, 3670 Oudsbergen", "Juli");
        foal.setClient(client);
        String[] medication = {"Panacur","Pyrantel","Eraquell","Panacur"};
        for (int i = 1; i < 5; i++ ){
            String title = "ontworming " + i + " (" + medication[i - 1] + ")";
            LocalDate date = foal.getDateOfBirth().plusMonths(i);
            Deworming deworming = new Deworming(title, false, date);
            foal.getDewormings().add(deworming);
        }
        daisy.setFoal(foal);

        Mare amy = new Mare("Amy", java.time.LocalDate.of(2013, 10, 5), 1.40, "Mare" );
        List<DiaryEntry> amyDiary = new ArrayList<>();
        amyDiary.add(new DiaryEntry("Amy is zwanger", LocalDateTime.of(2024, 1, 5, 10, 5)));
        amy.setDiary(amyDiary);

        amy.setPregnant(true);
        LocalDate dueDate = LocalDate.of(2024, 5, 20);
        int daysUntilDueDate = LocalDate.now().until(dueDate).getDays();
        amy.setDueDate(dueDate);
        amy.setDaysUntilDueDate(daysUntilDueDate);
        amy.setDaysPregnant(340 - daysUntilDueDate);
        Covering covering0 = new Covering(LocalDate.of(2024,5, 2));
        List<Covering> coverings = new ArrayList<>();
        coverings.add(covering0);
        amy.setCoverings(coverings);

        Mare tina = new Mare("Tina", java.time.LocalDate.of(2010, 4, 19), 1.52, "Mare");
        List<DiaryEntry> tinaDiary = new ArrayList<>();
        tinaDiary.add(new DiaryEntry("Tina is ziek", LocalDateTime.of(2024, 1, 5, 10, 5)));
        tina.setDiary(tinaDiary);
        Covering covering = new Covering(LocalDate.of(2024,5, 2));
        Covering covering1 = new Covering(LocalDate.of(2024,5, 10));
        Covering covering2 = new Covering(LocalDate.of(2024,5, 12));
        List<Covering> coverings1 = new ArrayList<>();
        coverings1.add(covering);
        coverings1.add(covering1);
        coverings1.add(covering2);
        tina.setCoverings(coverings1);


        Mare princess = new Mare("Princess", java.time.LocalDate.of(2010, 10, 4), 1.52, "Mare");
        List<DiaryEntry> princessDiary = new ArrayList<>();
        princessDiary.add(new DiaryEntry("princess is a princess <3", LocalDateTime.of(2024, 1, 5, 10, 5)));
        princess.setDiary(princessDiary);

        mareRepository.saveAll(List.of(daisy, amy, tina, princess));
    }
}
