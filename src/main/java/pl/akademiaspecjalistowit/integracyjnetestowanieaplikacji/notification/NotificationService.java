package pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.notification;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void saveNotification(String clientName, Long bookId) {
        NotificationEntity notification = new NotificationEntity(clientName, bookId);
        notificationRepository.save(notification);
    }
}
