package pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
