package be.pxl.researchproject.service;

import be.pxl.researchproject.api.response.NotificationDTO;
import be.pxl.researchproject.domain.Deworming;
import be.pxl.researchproject.domain.Foal;
import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.domain.Notification;
import be.pxl.researchproject.repository.FoalRepository;
import be.pxl.researchproject.repository.MareRepository;
import be.pxl.researchproject.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private MareRepository mareRepository;
    @Autowired
    private FoalRepository foalRepository;

    public void generateNotifications(List<Mare> mares, List<Foal> foals) {
        for (Mare mare : mares) {
            if (mare.isPregnant()) {
                if(mare.getDaysUntilDueDate() != null) {
                    int daysUntilDueDate = mare.getDaysUntilDueDate();
                    if (mare.getDueDate() != null && daysUntilDueDate <= 7) {
                        String title = "";
                        if (daysUntilDueDate < 0) {
                            title = "Merrie zou elk moment kunnen bevallen";
                        } else if (daysUntilDueDate == 0) {
                            title = "Merrie komt vandaag te bevallen";
                        } else if (daysUntilDueDate == 1) {
                            title = "Merrie komt morgen te bevallen";
                        } else {
                            title = "Merrie komt te bevallen binnen " + daysUntilDueDate + " dag(en)";
                        }
                        checkAndCreateNotification(title, LocalDate.now(), mare, null);
                    }
                }
            } else {
                // Remove all notifications related to mares that are no longer pregnant
                removeNotificationsForMare(mare);
            }
        }

        for (Foal foal : foals) {
            boolean hasValidDeworming = false;
            for (Deworming deworming : foal.getDewormings()) {
                LocalDate dewormingDate = deworming.getDate();
                LocalDate today = LocalDate.now();
                Optional<Mare> mareResult = mareRepository.findByFoalId(foal.getId());
                Mare mare = null;
                if (mareResult.isPresent()) {
                    mare = mareResult.get();
                }

                if (dewormingDate.getMonthValue() == today.getMonthValue() &&
                        dewormingDate.getDayOfMonth() - today.getDayOfMonth() == 0) {
                    checkAndCreateNotification(deworming.getTitle() + " moet vandaag worden toegediend", dewormingDate, mare, foal);
                    hasValidDeworming = true;
                }
            }
            if (!hasValidDeworming) {
                // Remove all notifications related to foals that no longer meet deworming requirements
                removeNotificationsForFoal(foal);
            }
        }
    }

    private void checkAndCreateNotification(String title, LocalDate date, Mare mare, Foal foal) {
        boolean notificationExists = notificationRepository.existsByTitleAndDateAndMareAndFoal(title, date, mare, foal);
        if (!notificationExists) {
            Notification notification = new Notification();
            notification.setTitle(title);
            notification.setDate(date);
            notification.setUnread(true);
            if (mare != null) {
                notification.setMare(mare);
            }
            if (foal != null) {
                notification.setFoal(foal);
            }
            notificationRepository.save(notification);
        }
    }

    private void removeNotificationsForMare(Mare mare) {
        List<Notification> notifications = notificationRepository.findByMare(mare);
        notificationRepository.deleteAll(notifications);
    }

    private void removeNotificationsForFoal(Foal foal) {
        List<Notification> notifications = notificationRepository.findByFoal(foal);
        notificationRepository.deleteAll(notifications);
    }

    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
    }

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream().map(notification -> {
            Long mareId = notification.getMare() != null ? notification.getMare().getId() : null;
            Long foalId = notification.getFoal() != null ? notification.getFoal().getId() : null;
            return new NotificationDTO(notification.getId(), notification.getTitle(), notification.isUnread(), notification.getDate(), mareId, foalId);
        }).collect(Collectors.toList());
    }

    public NotificationDTO markNotification(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.setUnread(!notification.isUnread());
        Notification savedNotif = notificationRepository.save(notification);
        return new NotificationDTO(savedNotif.getId(),
                savedNotif.getTitle(),
                savedNotif.isUnread(),
                savedNotif.getDate(),
                savedNotif.getMare().getId(),
                savedNotif.getFoal().getId());
    }

    public int getAmountOfUnreadNotifications() {
        return notificationRepository.findAll().stream().filter(Notification::isUnread).toList().size();
    }

    @Scheduled(fixedRate = 3600000)
    public void checkForNewNotifications() {
        List<Mare> mares = mareRepository.findAll();
        List<Foal> foals = foalRepository.findAll();
        generateNotifications(mares, foals);
    }
}
